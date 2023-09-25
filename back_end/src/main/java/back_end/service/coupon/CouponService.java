package back_end.service.coupon;

import back_end.exception.CustomException;
import back_end.mapper.coupon.CouponMapper;
import back_end.model.domain.Coupon;
import back_end.model.dto.request.CouponRequest;
import back_end.model.dto.response.CouponResponse;
import back_end.repository.ICouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponService implements ICouponService {
	
	@Autowired
	private ICouponRepository couponRepository;
	@Autowired
	private CouponMapper couponMapper;
	
	@Override
	public Page<CouponResponse> findAll(Pageable pageable, Optional<String> coupon) {
		List<CouponResponse> list = coupon.map(s -> couponRepository.findAllByCouponContainingIgnoreCase(pageable, s).stream()
				  .map(item -> couponMapper.toResponse(item))
				  .collect(Collectors.toList())).orElseGet(() -> couponRepository.findAll(pageable).stream()
				  .map(item -> couponMapper.toResponse(item))
				  .collect(Collectors.toList()));
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public CouponResponse findById(Long couponId) throws CustomException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		return optionalCoupon.map(item -> couponMapper.toResponse(item)).orElseThrow(() -> new CustomException("coupon not found"));
	}
	
	@Override
	public CouponResponse save(CouponRequest couponRequest) {
		return couponMapper.toResponse(couponRepository.save(couponMapper.toEntity(couponRequest)));
	}
	
	@Override
	public CouponResponse update(CouponRequest couponRequest, Long couponId) {
		Coupon coupon = couponMapper.toEntity(couponRequest);
		coupon.setId(couponId);
		return couponMapper.toResponse(couponRepository.save(coupon));
	}
	
	@Override
	public CouponResponse changeStatus(Long couponId) throws CustomException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (optionalCoupon.isPresent()) {
			Coupon coupon = optionalCoupon.get();
			coupon.setStatus(!coupon.isStatus());
			return couponMapper.toResponse(couponRepository.save(coupon));
		}
		throw new CustomException("coupon not found");
	}
}

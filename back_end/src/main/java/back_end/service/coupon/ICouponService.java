package back_end.service.coupon;

import back_end.exception.CustomException;
import back_end.model.dto.request.CouponRequest;
import back_end.model.dto.response.CouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICouponService {
	
	Page<CouponResponse> findAll(Pageable pageable, Optional<String> coupon);
	
	CouponResponse findById(Long couponId) throws CustomException;
	
	CouponResponse save(CouponRequest couponRequest);
	
	CouponResponse update(CouponRequest couponRequest, Long couponId);
	
	CouponResponse changeStatus(Long couponId) throws CustomException;
	
}

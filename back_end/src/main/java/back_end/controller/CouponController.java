package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.CouponRequest;
import back_end.model.dto.response.CouponResponse;
import back_end.service.coupon.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/coupon")
@CrossOrigin("*")
public class CouponController {
	
	@Autowired
	private ICouponService couponService;
	
	@GetMapping
	public ResponseEntity<Page<CouponResponse>> getAllCoupon(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
		return new ResponseEntity<>(couponService.findAll(pageable, search), HttpStatus.OK);
	}
	
	@GetMapping("/{couponId}")
	public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long couponId) throws CustomException {
		return new ResponseEntity<>(couponService.findById(couponId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CouponResponse> addNewCoupon(@RequestBody CouponRequest couponRequest) {
		return new ResponseEntity<>(couponService.save(couponRequest), HttpStatus.CREATED);
	}
	
	@PutMapping("/{couponId}")
	public ResponseEntity<CouponResponse> updateCoupon(@RequestBody CouponRequest couponRequest, @PathVariable Long couponId) {
		return new ResponseEntity<>(couponService.update(couponRequest, couponId), HttpStatus.OK);
	}
	
	@GetMapping("/{couponId}/status")
	public ResponseEntity<CouponResponse> changeStatusCoupon(@PathVariable Long couponId) throws CustomException {
		return new ResponseEntity<>(couponService.changeStatus(couponId), HttpStatus.OK);
	}
	
}

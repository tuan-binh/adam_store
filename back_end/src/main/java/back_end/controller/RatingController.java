package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.request.RatingRequest;
import back_end.model.dto.response.OrderResponse;
import back_end.model.dto.response.RatingResponse;
import back_end.service.rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RatingController {
	
	@Autowired
	private IRatingService ratingService;
	
	@PostMapping("/user/{orderId}/rating")
	public ResponseEntity<OrderResponse> handleRatingOrder(@PathVariable Long orderId, @RequestBody RatingRequest ratingRequest, Authentication authentication) throws CustomException {
		return new ResponseEntity<>(ratingService.ratingOrder(orderId, ratingRequest, authentication), HttpStatus.CREATED);
	}
	
	
	
}

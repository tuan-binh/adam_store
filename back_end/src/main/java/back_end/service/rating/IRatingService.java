package back_end.service.rating;

import back_end.exception.CustomException;
import back_end.model.dto.request.RatingRequest;
import back_end.model.dto.response.OrderResponse;
import back_end.model.dto.response.RatingResponse;
import org.springframework.security.core.Authentication;

public interface IRatingService {
	
	OrderResponse ratingOrder(Long orderId, RatingRequest ratingRequest, Authentication authentication) throws CustomException;
	
}

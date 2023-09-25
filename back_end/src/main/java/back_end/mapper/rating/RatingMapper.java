package back_end.mapper.rating;

import back_end.exception.CustomException;
import back_end.mapper.IGenericMapper;
import back_end.model.domain.Orders;
import back_end.model.domain.Rating;
import back_end.model.dto.request.RatingRequest;
import back_end.model.dto.response.RatingResponse;
import back_end.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("rating")
public class RatingMapper implements IGenericMapper<Rating, RatingRequest, RatingResponse> {
	
	@Autowired
	private IOrderRepository orderRepository;
	
	@Override
	public Rating toEntity(RatingRequest ratingRequest) throws CustomException {
		return Rating.builder()
				  .rate(ratingRequest.getRate())
				  .content(ratingRequest.getContent())
				  .created(ratingRequest.getCreated())
				  .build();
	}
	
	@Override
	public RatingResponse toResponse(Rating rating) throws CustomException {
		return RatingResponse.builder()
				  .id(rating.getId())
				  .username(findUsernameByRatingIdInOrder(rating.getId()))
				  .rate(rating.getRate())
				  .content(rating.getContent())
				  .created(rating.getCreated())
				  .build();
	}
	
	public String findUsernameByRatingIdInOrder(Long ratingId) throws CustomException {
		Optional<Orders> optionalOrders = orderRepository.findByRatingId(ratingId);
		if (optionalOrders.isPresent()) {
			return optionalOrders.get().getUsers().getEmail();
		}
		throw new CustomException("order not found");
	}
	
}

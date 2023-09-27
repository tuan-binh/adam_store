package back_end.service.rating;

import back_end.exception.CustomException;
import back_end.mapper.rating.RatingMapper;
import back_end.model.domain.Orders;
import back_end.model.domain.Rating;
import back_end.model.dto.request.RatingRequest;
import back_end.model.dto.response.OrderResponse;
import back_end.model.dto.response.RatingResponse;
import back_end.repository.IOrderRepository;
import back_end.repository.IRatingRepository;
import back_end.security.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService implements IRatingService {
	
	@Autowired
	private IRatingRepository ratingRepository;
	@Autowired
	private RatingMapper ratingMapper;
	@Autowired
	private IOrderRepository orderRepository;
	
	@Override
	public OrderResponse ratingOrder(Long orderId, RatingRequest ratingRequest, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByIdAndUsersIdAndStatus(orderId, userPrinciple.getId(), true);
		if (optionalOrders.isPresent()) {
			Rating rating = ratingMapper.toEntity(ratingRequest);
			Orders order = optionalOrders.get();
			order.setRating(rating);
			return OrderToResponse(orderRepository.save(order));
		}
		throw new CustomException("order not found");
	}
	
	public OrderResponse OrderToResponse(Orders orders) {
		return OrderResponse.builder()
				  .id(orders.getId())
				  .typeDelivery(orders.getTypeDelivery())
				  .timeDelivery(orders.getTimeDelivery())
				  .location(orders.getLocation())
				  .phone(orders.getPhone())
				  .total(orders.getTotal())
				  .rating(orders.getRating())
				  .status(orders.isStatus())
				  .build();
	}
	
	
	
}

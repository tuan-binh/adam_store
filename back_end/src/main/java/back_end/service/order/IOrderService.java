package back_end.service.order;

import back_end.exception.CustomException;
import back_end.model.dto.response.OrderDetailResponse;
import back_end.model.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
	
	Page<OrderResponse> findAll(Pageable pageable, Optional<String> phone);
	
	Page<OrderResponse> findAllOnUser(Pageable pageable, Authentication authentication);
	
	List<OrderDetailResponse> getCartByUser(Authentication authentication) throws CustomException;
	
	List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId, Authentication authentication) throws CustomException;
	
	List<OrderDetailResponse> buyProductByProductDetailId(Long productDetailId, Authentication authentication) throws CustomException;
	
	List<OrderDetailResponse> plusProductDetail(Long orderDetailId, Authentication authentication);
	
	List<OrderDetailResponse> minusProductDetail(Long orderDetailId, Authentication authentication);
	
}

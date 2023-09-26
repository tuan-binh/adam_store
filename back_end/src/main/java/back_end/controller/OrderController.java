package back_end.controller;

import back_end.exception.CustomException;
import back_end.model.dto.response.OrderDetailResponse;
import back_end.model.dto.response.OrderResponse;
import back_end.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/admin/orders")
	public ResponseEntity<Page<OrderResponse>> getAllOrdersByAdmin(@PageableDefault(page = 0, size = 3) Pageable pageable, @RequestParam Optional<String> search) {
		return new ResponseEntity<>(orderService.findAll(pageable, search), HttpStatus.OK);
	}
	
	@GetMapping("/user/orders")
	public ResponseEntity<Page<OrderResponse>> getAllOrderByUser(@PageableDefault(page = 0, size = 3) Pageable pageable, Authentication authentication) {
		return new ResponseEntity<>(orderService.findAllOnUser(pageable, authentication), HttpStatus.OK);
	}
	
	@GetMapping("/user/order/{orderId}")
	public ResponseEntity<List<OrderDetailResponse>> getOrderDetailByOrderId(@PathVariable Long orderId, Authentication authentication) throws CustomException {
		return new ResponseEntity<>(orderService.getOrderDetailByOrderId(orderId, authentication), HttpStatus.OK);
	}
	
	@GetMapping("/user/cart")
	public ResponseEntity<List<OrderDetailResponse>> getCartByUser(Authentication authentication) throws CustomException {
		return new ResponseEntity<>(orderService.getCartByUser(authentication), HttpStatus.OK);
	}
	
	@PostMapping("/user/buy/{productDetailId}")
	public ResponseEntity<List<OrderDetailResponse>> buyProductByProductDetailId(@PathVariable Long productDetailId, Authentication authentication) throws CustomException {
		return new ResponseEntity<>(orderService.buyProductByProductDetailId(productDetailId, authentication), HttpStatus.CREATED);
	}
	
	@PutMapping("/user/plus/{orderDetailId}")
	public ResponseEntity<List<OrderDetailResponse>> plusProductDetail(@PathVariable Long orderDetailId, Authentication authentication) {
		return new ResponseEntity<>(orderService.plusProductDetail(orderDetailId, authentication), HttpStatus.OK);
	}
	
	@PutMapping("/user/minus/{orderDetailId}")
	public ResponseEntity<List<OrderDetailResponse>> minusProductDetail(@PathVariable Long orderDetailId, Authentication authentication) {
		return new ResponseEntity<>(orderService.minusProductDetail(orderDetailId, authentication), HttpStatus.OK);
	}
	
}

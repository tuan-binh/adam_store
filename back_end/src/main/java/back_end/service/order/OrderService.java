package back_end.service.order;

import back_end.exception.CustomException;
import back_end.model.domain.*;
import back_end.model.dto.response.OrderDetailResponse;
import back_end.model.dto.response.OrderResponse;
import back_end.repository.IOrderDetailRepository;
import back_end.repository.IOrderRepository;
import back_end.repository.IProductDetailRepository;
import back_end.repository.IUserRepository;
import back_end.security.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private IOrderDetailRepository orderDetailRepository;
	@Autowired
	private IProductDetailRepository productDetailRepository;
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public Page<OrderResponse> findAll(Pageable pageable, Optional<String> phone) {
		List<OrderResponse> list = phone.map(s -> orderRepository.findAllByPhoneContainingIgnoreCaseAndStatus(pageable, s, true).stream()
				  .map(this::OrderToResponse)
				  .collect(Collectors.toList())).orElseGet(() -> orderRepository.findAll(pageable).stream()
				  .map(this::OrderToResponse)
				  .collect(Collectors.toList()));
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public Page<OrderResponse> findAllOnUser(Pageable pageable, Authentication authentication) {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		List<OrderResponse> list = orderRepository.findAllByUsersIdAndStatus(pageable, userPrinciple.getId(), true).stream()
				  .map(this::OrderToResponse)
				  .collect(Collectors.toList());
		return new PageImpl<>(list, pageable, list.size());
	}
	
	@Override
	public List<OrderDetailResponse> getCartByUser(Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (optionalOrders.isPresent()) {
			return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
					  .map(this::CartItemToResponse)
					  .collect(Collectors.toList());
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByIdAndUsersIdAndStatus(orderId, userPrinciple.getId(), true);
		if (optionalOrders.isPresent()) {
			return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
					  .map(this::OrderDetailToResponse)
					  .collect(Collectors.toList());
		}
		throw new CustomException("order not found");
	}
	
	@Override
	public List<OrderDetailResponse> buyProductByProductDetailId(Long productDetailId, Authentication authentication) throws CustomException {
		ProductDetail productDetail = findProductDetailById(productDetailId);
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (!optionalOrders.isPresent()) {
			// cart khong ton tai thi khoi tao
			Orders order = orderRepository.save(Orders.builder()
					  .typeDelivery(TypeDelivery.PENDING)
					  .users(findUserByUserName(userPrinciple.getUsername()))
					  .status(false)
					  .build());
			orderDetailRepository.save(OrderDetail.builder()
					  .quantity(1)
					  .orders(order)
					  .productDetail(productDetail)
					  .build());
			return orderDetailRepository.findAllByOrdersId(order.getId()).stream()
					  .map(this::CartItemToResponse)
					  .collect(Collectors.toList());
		}
		// co ton tai cart thi kiem tra co productdetail khong
		Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findByOrdersIdAndProductDetailId(optionalOrders.get().getId(), productDetailId);
		OrderDetail orderDetail;
		if (optionalOrderDetail.isPresent()) {
			// san pham nay co trong gio hang roi
			orderDetail = optionalOrderDetail.get();
			orderDetail.setQuantity(orderDetail.getQuantity() + 1);
		} else {
			// san pham chua co trong gio hang
			orderDetail = OrderDetail.builder().quantity(1).orders(optionalOrders.get()).productDetail(productDetail).build();
		}
		orderDetailRepository.save(orderDetail);
		return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
				  .map(this::CartItemToResponse)
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<OrderDetailResponse> plusProductDetail(Long orderDetailId, Authentication authentication) {
		
		return null;
	}
	
	@Override
	public List<OrderDetailResponse> minusProductDetail(Long orderDetailId, Authentication authentication) {
		return null;
	}
	
	public ProductDetail findProductDetailById(Long productDetailId) throws CustomException {
		Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(productDetailId);
		return optionalProductDetail.orElseThrow(() -> new CustomException("product detail not found"));
	}
	
	public OrderResponse OrderToResponse(Orders orders) {
		return OrderResponse.builder()
				  .id(orders.getId())
				  .typeDelivery(orders.getTypeDelivery())
				  .timeDelivery(orders.getTimeDelivery())
				  .location(orders.getLocation())
				  .phone(orders.getPhone())
				  .total(orders.getTotal())
				  .status(orders.isStatus())
				  .build();
	}
	
	public OrderDetailResponse CartItemToResponse(OrderDetail orderDetail) {
		return OrderDetailResponse.builder()
				  .id(orderDetail.getId())
				  .productName(orderDetail.getProductDetail().getProduct().getProductName())
				  .url(orderDetail.getProductDetail().getProduct().getImage())
				  .price(orderDetail.getProductDetail().getPrice())
				  .quantity(orderDetail.getQuantity())
				  .build();
	}
	
	public OrderDetailResponse OrderDetailToResponse(OrderDetail orderDetail) {
		return OrderDetailResponse.builder()
				  .id(orderDetail.getId())
				  .productName(orderDetail.getProductDetail().getProduct().getProductName())
				  .url(orderDetail.getProductDetail().getProduct().getImage())
				  .price(orderDetail.getPrice())
				  .quantity(orderDetail.getQuantity())
				  .build();
	}
	
	public Users findUserByUserName(String username) throws CustomException {
		Optional<Users> optionalUsers = userRepository.findByEmail(username);
		return optionalUsers.orElseThrow(() -> new CustomException("username not found"));
	}
	
}

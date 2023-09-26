package back_end.service.order;

import back_end.exception.CustomException;
import back_end.model.domain.*;
import back_end.model.dto.request.CheckoutRequest;
import back_end.model.dto.response.OrderDetailResponse;
import back_end.model.dto.response.OrderResponse;
import back_end.repository.*;
import back_end.security.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
	@Autowired
	private ICouponRepository couponRepository;
	@Autowired
	private IProductRepository productRepository;
	
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
	public List<OrderDetailResponse> plusProductDetail(Long orderDetailId, Authentication authentication) throws CustomException {
		OrderDetail orderDetail = findOrderDetailById(orderDetailId);
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (optionalOrders.isPresent()) {
			if (Objects.equals(orderDetail.getOrders().getId(), optionalOrders.get().getId())) {
				// kiem tra trong kho con du san pham khong
				if (orderDetail.getQuantity() >= orderDetail.getProductDetail().getStock()) {
					throw new CustomException("product detail not enough");
				}
				orderDetail.setQuantity(orderDetail.getQuantity() + 1);
				orderDetailRepository.save(orderDetail);
				return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
						  .map(this::CartItemToResponse)
						  .collect(Collectors.toList());
			}
			throw new CustomException("Un permission");
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public List<OrderDetailResponse> minusProductDetail(Long orderDetailId, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (optionalOrders.isPresent()) {
			OrderDetail orderDetail = findOrderDetailById(orderDetailId);
			if (Objects.equals(orderDetail.getOrders().getId(), optionalOrders.get().getId())) {
				if (orderDetail.getQuantity() == 1) {
					orderDetailRepository.deleteById(orderDetailId);
					return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
							  .map(this::CartItemToResponse)
							  .collect(Collectors.toList());
				}
				orderDetail.setQuantity(orderDetail.getQuantity() - 1);
				orderDetailRepository.save(orderDetail);
				return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
						  .map(this::CartItemToResponse)
						  .collect(Collectors.toList());
			}
			throw new CustomException("Un permission");
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public List<OrderDetailResponse> removeCartItemInCart(Long orderDetailId, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (optionalOrders.isPresent()) {
			orderDetailRepository.deleteById(orderDetailId);
			return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
					  .map(this::CartItemToResponse)
					  .collect(Collectors.toList());
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public List<OrderDetailResponse> removeAllInCart(Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		if (optionalOrders.isPresent()) {
			orderDetailRepository.resetAllByOrderId(optionalOrders.get().getId());
			return orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()).stream()
					  .map(this::CartItemToResponse)
					  .collect(Collectors.toList());
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public OrderResponse checkoutOrder(CheckoutRequest checkoutRequest, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByUsersIdAndStatus(userPrinciple.getId(), false);
		Orders order;
		if (optionalOrders.isPresent()) {
			order = optionalOrders.get();
			if (orderDetailRepository.findAllByOrdersId(order.getId()).isEmpty()) {
				throw new CustomException("cart is empty");
			}
			for (OrderDetail od : orderDetailRepository.findAllByOrdersId(order.getId())) {
				od.setPrice(od.getProductDetail().getPrice());
				orderDetailRepository.save(od);
			}
			order.setTimeDelivery(new Date());
			order.setTypeDelivery(TypeDelivery.PREPARE);
			order.setStatus(true);
			Coupon coupon = null;
			if (checkoutRequest.getCouponId() != null) {
				coupon = findCouponById(checkoutRequest.getCouponId());
			}
			if (checkoutRequest.getAddress() == null || checkoutRequest.getPhone() == null) {
				if (userPrinciple.getAddress() == null || userPrinciple.getPhone() == null) {
					throw new CustomException("you must be update your info");
				}
				// checkout with your infor
				order.setLocation(userPrinciple.getAddress());
				order.setPhone(userPrinciple.getPhone());
			} else {
				// check out with other address and phone
				order.setLocation(checkoutRequest.getAddress());
				order.setPhone(checkoutRequest.getPhone());
			}
			order.setTotal(orderDetailRepository.findAllByOrdersId(order.getId()).stream().map(item -> item.getQuantity() * item.getPrice()).reduce((double) 0, Double::sum));
			if (coupon != null) {
				boolean check = checkTimeCoupon(coupon);
				if (check) {
					order.setCoupon(coupon);
					order.setTotal(order.getTotal() - (order.getTotal() * (coupon.getPercent() / 100)));
					coupon.setStock(coupon.getStock() - 1);
					couponRepository.save(coupon);
				}
			} else {
				throw new CustomException("Discount code has expired");
			}
			minusStockInProductDetail(orderDetailRepository.findAllByOrdersId(order.getId()));
			return OrderToResponse(orderRepository.save(order));
		}
		throw new CustomException("cart is empty");
	}
	
	@Override
	public OrderResponse cancelOrder(Long orderId, Authentication authentication) throws CustomException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		Optional<Orders> optionalOrders = orderRepository.findByIdAndUsersIdAndStatus(orderId, userPrinciple.getId(), true);
		if (optionalOrders.isPresent()) {
			optionalOrders.get().setTimeDelivery(new Date());
			returnStockProductDetail(orderDetailRepository.findAllByOrdersId(optionalOrders.get().getId()));
			return changeDelivery("cancel", orderId);
		}
		throw new CustomException("Un Permisson");
	}
	
	@Override
	public OrderResponse changeDelivery(String typeDelivery, Long orderId) throws CustomException {
		Orders order = findOrderById(orderId);
		TypeDelivery type = findDeliveryByInput(typeDelivery);
		if (order.getTypeDelivery().toString().equals("PENDING")) {
			if (type.toString().equals("DELIVERY") || type.toString().equals("SUCCESS")) {
				throw new CustomException("Your order is in status PENDING cannot switch to state " + type);
			}
			order.setTypeDelivery(type);
		} else if (order.getTypeDelivery().toString().equals("PREPARE")) {
			if (type.toString().equals("PENDING") || type.toString().equals("SUCCESS")) {
				throw new CustomException("Your order is in status PREPARE cannot switch to state " + type);
			}
			order.setTypeDelivery(type);
		} else if (order.getTypeDelivery().toString().equals("DELIVERY")) {
			if (type.toString().equals("PENDING") || type.toString().equals("PREPARE") || type.toString().equals("CANCEL")) {
				throw new CustomException("Your order is in status DELIVERY cannot switch to state " + type);
			}
			order.setTypeDelivery(type);
		} else if (order.getTypeDelivery().toString().equals("SUCCESS")) {
			if (type.toString().equals("PENDING") || type.toString().equals("PREPARE") || type.toString().equals("DELIVERY") || type.toString().equals("CANCEL")) {
				throw new CustomException("Your order is in status SUCCESS cannot switch to state " + type);
			}
			order.setTypeDelivery(type);
			// thực hiện thêm số lượng đã mua vào database
			uptoCountByInProduct(orderDetailRepository.findAllByOrdersId(orderId));
		} else {
			if (type.toString().equals("PENDING") || type.toString().equals("PREPARE") || type.toString().equals("DELIVERY") || type.toString().equals("SUCCESS")) {
				throw new CustomException("Your order is in status CANCEL cannot switch to state " + type);
			}
			returnStockProductDetail(orderDetailRepository.findAllByOrdersId(orderId));
			order.setTypeDelivery(type);
		}
		return OrderToResponse(orderRepository.save(order));
	}
	
	public TypeDelivery findDeliveryByInput(String typeDelivery) throws CustomException {
		switch (typeDelivery) {
			case "pending":
				return TypeDelivery.PENDING;
			case "prepare":
				return TypeDelivery.PREPARE;
			case "delivery":
				return TypeDelivery.DELIVERY;
			case "success":
				return TypeDelivery.SUCCESS;
			case "cancel":
				return TypeDelivery.CANCEL;
			default:
				throw new CustomException("delivery not found");
		}
	}
	
	public void uptoCountByInProduct(List<OrderDetail> orderDetails) {
		for (OrderDetail od : orderDetails) {
			od.getProductDetail().getProduct().setBought(od.getProductDetail().getProduct().getBought() + od.getQuantity());
			productRepository.save(od.getProductDetail().getProduct());
		}
	}
	
	public void minusStockInProductDetail(List<OrderDetail> orderDetails) {
		for (OrderDetail od : orderDetails) {
			od.getProductDetail().setStock(od.getProductDetail().getStock() - od.getQuantity());
			productDetailRepository.save(od.getProductDetail());
		}
	}
	
	public void returnStockProductDetail(List<OrderDetail> orderDetails) {
		for (OrderDetail od : orderDetails) {
			od.getProductDetail().setStock(od.getProductDetail().getStock() + od.getQuantity());
			productDetailRepository.save(od.getProductDetail());
		}
	}
	
	public Orders findOrderById(Long orderId) throws CustomException {
		Optional<Orders> optionalOrders = orderRepository.findById(orderId);
		return optionalOrders.orElseThrow(() -> new CustomException("order not found"));
	}
	
	public boolean checkTimeCoupon(Coupon coupon) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate startDateCoupon = LocalDate.parse(coupon.getStartDate().toString(), formatter);
		LocalDate endDateCoupon = LocalDate.parse(coupon.getEndDate().toString(), formatter);
		LocalDate timeNow = LocalDate.parse(new Date().toString(), formatter);
		return !timeNow.isAfter(endDateCoupon) && !timeNow.isBefore(startDateCoupon);
	}
	
	public Coupon findCouponById(Long couponId) throws CustomException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		return optionalCoupon.orElseThrow(() -> new CustomException("coupon not found"));
	}
	
	public OrderDetail findOrderDetailById(Long orderDetailId) throws CustomException {
		Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetailId);
		return optionalOrderDetail.orElseThrow(() -> new CustomException("order detail not found"));
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

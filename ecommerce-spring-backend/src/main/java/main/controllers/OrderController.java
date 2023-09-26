package main.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.entity.Order;
import main.entity.User;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.requests.OrderRequest;
import main.service.OrderService;
import main.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private OrderService orderService;
	private UserService userService;
	

	public OrderController(OrderService orderService,  UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}


	@PostMapping("/userProfile")
	public ResponseEntity<?> getUserProfileHandler(@RequestHeader("Authorization") String jwt){
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUserProfileByJwt(jwt));
	}
	
	
	@PostMapping("")
	public ResponseEntity<Order> createOrderHandler(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest orderRequest) throws OrderException, CartItemException, CartException{
		
		User user = userService.findUserProfileByJwt(jwt);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(user, orderRequest));
	}
	
	
	@PreAuthorize("hasAnyAuthority('ADMIN', 'admin')")
	@GetMapping("")
	public ResponseEntity<Page<Order>> getAllOrderHander(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="12") Integer pageSize){
			
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(page, pageSize));
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> findOrderByIdHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrderyId(orderId));
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Boolean> deleteOrderByIdHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.deleteOrderAdmin(orderId));
	}
	
	
	
	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<Boolean> cancelOrderHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(orderId));
	}
	
	@PostMapping("/{orderId}/delivered")
	public ResponseEntity<Boolean>  deliveredOrderHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.deliveredOrder(orderId));
	}
	
	@PostMapping("/{orderId}/ship")
	public ResponseEntity<Boolean>  shipdOrderHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.shippedOrder(orderId));
	}
	
	@PostMapping("/{orderId}/confirme")
	public ResponseEntity<Boolean>  confirmedOrderHandler(@PathVariable int orderId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.confirmedOrder(orderId));
	}
	
	@PostMapping("/{orderId}/retuned")
	public ResponseEntity<?> returnedOrderHandler(@PathVariable("orderId") int orderId){
		orderService.returnedOrder(orderId);
		
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
	
	
	@PostMapping("/users/{userId}")
	public ResponseEntity<List<Order>> findUserOrders(@PathVariable int userId){
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findUserOrders(userId));
	}
}

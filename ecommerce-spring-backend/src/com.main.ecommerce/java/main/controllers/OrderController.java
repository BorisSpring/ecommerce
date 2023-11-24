package main.controllers;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import main.domain.Order;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.requests.OrderRequest;
import main.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

	private final OrderService orderService;

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public void createOrderHandler(@Valid @RequestBody OrderRequest orderRequest,
                                                    @RequestHeader("Authorization") String jwt) throws OrderException, CartItemException, CartException{
        orderService.createOrder(jwt, orderRequest);
	}

	@GetMapping
	public ResponseEntity<Page<Order>> getAllOrderHander(@Positive(message = "Page must be greater then zero!") @RequestParam(defaultValue="1", required = false)Integer page,
                                                         @Positive(message = "Page size must be greater then zero!") @RequestParam(defaultValue="12", required = false) Integer pageSize){
        return ResponseEntity.ok(orderService.findAllOrders(PageRequest.of((page - 1), pageSize)));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> findOrderByIdHandler(@Positive(message = "Order id must be greater then zero !") @PathVariable int orderId){
        return  ResponseEntity.ok(orderService.findOrderById(orderId));
	}

	@DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrderByIdHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId){
        orderService.deleteOrderAdmin(orderId);
	}


	@PatchMapping("/status/{cancel}")
    @ResponseStatus(HttpStatus.OK)
	public void cancelOrderHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId,
                                   @RequestHeader("Authorization") String jwt ){
        orderService.cancelOrder(orderId, jwt);
	}

	@PatchMapping("/status/delivered")
    @ResponseStatus(HttpStatus.OK)
	public void  deliveredOrderHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId){
        orderService.deliveredOrder(orderId);
	}

	@PatchMapping("/status/ship")
    @ResponseStatus(HttpStatus.OK)
	public void  shipdOrderHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId){
        orderService.shippedOrder(orderId);
	}

	@PatchMapping("/status/confirme")
    @ResponseStatus(HttpStatus.OK)
	public void  confirmedOrderHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId){
		orderService.confirmedOrder(orderId);
	}

	@PatchMapping("/status/retuned")
    @ResponseStatus(HttpStatus.OK)
	public void returnedOrderHandler(@Positive(message = "Order id must be greater then zero!") @RequestParam int orderId){
		orderService.returnedOrder(orderId);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Order>> findUserOrders(@RequestHeader("Authorization") String jwt){
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findUserOrders(jwt));
	}
}

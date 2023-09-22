package main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.entity.CartItem;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.ProductException;
import main.exception.UserException;
import main.requests.AddItemRequest;
import main.requests.UpdateCartItemRequest;
import main.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

	private CartService cartService;
	

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/addCartItem")
	public ResponseEntity<Boolean> addCartItemHandler(@RequestBody AddItemRequest req) throws  ProductException, CartException, CartItemException{
		
		return ResponseEntity.status(HttpStatus.OK).body(cartService.addCartItem( req));
	}
	
	@PostMapping("/updateCartItem")
	public ResponseEntity<CartItem> updateCartItemHandler(@RequestBody UpdateCartItemRequest req) throws UserException, CartItemException, CartException{
		
		return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartItem(req));
	}
	
	@DeleteMapping("/{userId}/{cartItemId}")
	public ResponseEntity<Boolean> deleteCartItemHandler(@PathVariable int userId , @PathVariable int cartItemId) throws UserException, CartItemException, CartException{
		
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cartService.removeCartItem(userId, cartItemId));
	}
	
}

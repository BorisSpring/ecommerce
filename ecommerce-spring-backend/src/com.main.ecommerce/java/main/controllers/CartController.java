package main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main.domain.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.ProductException;
import main.exception.UserException;
import main.requests.CreateCartItemRequest;
import main.requests.UpdateCartItemRequest;
import main.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Validated
public class CartController {

	private final CartService cartService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addCartItemHandler(@Valid  @RequestBody CreateCartItemRequest createCartItemRequest,
								   @RequestHeader("Authorization") String jwt) throws  ProductException, CartException, CartItemException{
		cartService.addCartItem(createCartItemRequest, jwt);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateCartItemHandler(@Valid @RequestBody UpdateCartItemRequest updateCartItemRequest,
									  @RequestHeader("Authorization") String jwt) throws UserException, CartItemException, CartException{
		cartService.updateCartItem(updateCartItemRequest, jwt);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCartItemHandler(@Positive(message = "Cart item id must be greather then zero!") @RequestParam Integer cartItemId,
									  @RequestHeader("Authorization") String jwt) throws UserException, CartItemException, CartException{
		cartService.deleteCartItem(cartItemId, jwt);
	}

	@GetMapping
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws CartException {
		return ResponseEntity.ok(cartService.findUserCart(jwt));
	}
	
}

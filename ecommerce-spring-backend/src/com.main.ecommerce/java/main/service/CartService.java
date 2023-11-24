package main.service;

import main.domain.Cart;
import main.domain.CartItem;
import main.domain.Product;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.ProductException;
import main.exception.UserException;
import main.requests.CreateCartItemRequest;
import main.requests.UpdateCartItemRequest;
import java.util.UUID;

public interface CartService {


	 Cart findUserCart(String jwt) throws CartException;

	void addCartItem( CreateCartItemRequest createCartItemRequest, String jwt) throws ProductException, CartException, CartItemException;

	void updateCartItem(UpdateCartItemRequest updateCartItemRequest, String jwt) throws CartItemException , UserException, CartException;

	void deleteCartItem(Integer cartItemId, String jwt) throws CartItemException;

	CartItem findByCartItemById(int cartItemId) throws CartItemException;

	void createCart(UUID userId) throws CartException;

	boolean isCartItemExist( Product product , String size, UUID userId);
}

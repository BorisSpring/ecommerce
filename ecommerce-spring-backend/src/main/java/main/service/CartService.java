package main.service;

import main.entity.Cart;
import main.entity.CartItem;
import main.entity.Product;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.ProductException;
import main.exception.UserException;
import main.requests.AddItemRequest;
import main.requests.UpdateCartItemRequest;

public interface CartService {

	public Cart createCart(int userId) throws CartException;
	
	public boolean addCartItem( AddItemRequest req) throws ProductException, CartException, CartItemException;
	
	public Cart findUserCart(int userId) throws CartException;
		
	public CartItem updateCartItem(UpdateCartItemRequest req) throws CartItemException , UserException, CartException;
	
	public CartItem isCartItemExist(Cart cart , Product product , String size, int userId);

	public boolean removeCartItem( int userId, int cartItemId) throws CartItemException, CartException;
	
	public CartItem findByCartItemById(int cartItemId) throws CartItemException;

	
	void deleteAllByUserId(int userId);

	
}

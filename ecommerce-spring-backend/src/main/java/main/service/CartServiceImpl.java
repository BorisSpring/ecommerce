package main.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.entity.Cart;
import main.entity.CartItem;
import main.entity.Product;
import main.entity.User;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.ProductException;
import main.exception.UserException;
import main.repository.CartItemRepository;
import main.repository.CartRepository;
import main.requests.AddItemRequest;
import main.requests.UpdateCartItemRequest;

@Service
public class CartServiceImpl implements CartService{

	private CartRepository cartRepo;
	private CartItemRepository cartItemRepo;
	private UserService userService;
	private ProductService productService;
	
	
	

	public CartServiceImpl(CartRepository cartRepo, CartItemRepository cartItemRepo, UserService userService,
			ProductService productService) {
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.userService = userService;
		this.productService = productService;
	}

	@Transactional
	@Override
	public Cart createCart(int userId) throws CartException {
		
		Cart userCart = cartRepo.findByUserId(userId);
		
		if(userCart != null) {
			return userCart;
		}
		
		User user = userService.findUserById(userId);
		
		Cart cart = new Cart();
		cart.setUser(user);
		
		Cart savedCart = cartRepo.save(cart);
		
		if(savedCart == null) {
			throw new CartException("Failed to create cart!");
		}
		return cart;
	}

	@Transactional
	@Override
	public boolean addCartItem( AddItemRequest req) throws ProductException, CartException, CartItemException {
		
		Cart userCart = findUserCart(req.getUserId());
		User user = userService.findUserById(req.getUserId());
		Product product = productService.findProductById(req.getProductId());
		
		CartItem isPresent = isCartItemExist(userCart, product, req.getSize(), req.getUserId());
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setCart(userCart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setPrice(req.getQuantity() * product.getPrice());
			if(product.getDiscountPrecent() > 0) {
				cartItem.setDiscountPrice((req.getQuantity() * product.getDiscountPrice()));
				
				cartItem.setDiscount(Math.abs(req.getQuantity() * (cartItem.getPrice() - cartItem.getDiscountPrice())));
			}
			cartItem.setTotalPrice(cartItem.getPrice() - cartItem.getDiscountPrice());
			cartItem.setSize(req.getSize());
		
			
			userCart.getCartItems().add(cartItem);
			
			updateCartOptions(userCart);
			return true;
		}
			
		return false;
			
	}

	@Override
	public Cart findUserCart(int userId) throws CartException {
		
		Cart userCart = cartRepo.findByUserId(userId);
		System.out.println(userId);
		if(userCart == null) {
			throw new CartException("User doesnt have cart!");
		}
		
		
		return userCart;
	}


	
	@Override
	public CartItem updateCartItem(UpdateCartItemRequest req) throws CartItemException, UserException, CartException {
		
		CartItem cartItem = findByCartItemById(req.getCartItemId());
		
		if(cartItem.getUser().getId() != req.getUserId()) {
			throw new UserException("User is not related to this cart item");
		}
		
		if(req.getQuantity() == 0 ) {
			cartItemRepo.deleteById(req.getCartItemId());
			return null;
		}
		
		cartItem.setQuantity(req.getQuantity());
		cartItem.setSize(req.getSize());
		cartItem.setPrice(req.getQuantity() * cartItem.getProduct().getPrice());
		
		if(cartItem.getDiscount() > 0) {			
			cartItem.setDiscount(req.getQuantity() * cartItem.getProduct().getDiscountPrice());
			cartItem.setDiscountPrice(Math.abs(cartItem.getDiscount() - cartItem.getPrice()));
		}
		
		CartItem updatedCartItem = cartItemRepo.save(cartItem);
		
		if(updatedCartItem == null) {
			throw new CartItemException("Failed to update cart item!");
		}
		
		updateCartOptions(updatedCartItem.getCart());
		return updatedCartItem;
	}

	
	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, int userId) {
		return cartItemRepo.isCartItemExist(cart, product, size, userId);
	}

	
	
	@Override
	@Transactional
	public boolean removeCartItem(int userId, int cartItemId) throws CartItemException, CartException {
		
		Cart userCart = findUserCart(userId);
		
		CartItem cartItem = findByCartItemById(cartItemId);
		
		if(cartItem.getCart().getId() == userCart.getId() && userCart.getCartItems().contains(cartItem)) {	
			userCart.getCartItems().removeIf(p -> p.getId() == cartItemId);
			cartItemRepo.deleteById(cartItem.getId());
			updateCartOptions(userCart);
			return true;
		}
		throw new CartItemException("Cart item is not related to this user or doesnt exist!");
	}

	
	
	@Override
	public CartItem findByCartItemById(int cartItemId) throws CartItemException {

		Optional<CartItem> item = cartItemRepo.findById(cartItemId);
		
		if(item.isPresent()){
			return item.get();
		}
		
		throw new CartItemException("Cart item with id " + cartItemId + " doestn exist");
	}

	
	public void updateCartOptions(Cart userCart) throws CartException {

		userCart.setDiscount(0);
		userCart.setTotalDiscountPrice(0);
		userCart.setTotalQuantity(0);
		userCart.setTotalPrice(0);
		
		for(CartItem cartItems: userCart.getCartItems()) {
			userCart.setDiscount(userCart.getDiscount() + cartItems.getDiscount());
			userCart.setTotalPrice(userCart.getTotalPrice() + cartItems.getPrice());
			userCart.setTotalDiscountPrice(userCart.getTotalPrice() - userCart.getDiscount());
			userCart.setTotalQuantity(userCart.getTotalQuantity() + cartItems.getQuantity());
		}
		
		Cart updatedCart = cartRepo.save(userCart);
		
		if(updatedCart == null) {
			throw new CartException("Fail to update cart options");
		}
	}
	
	
	@Override
	public void deleteAllByUserId(int userId) {
		cartItemRepo.deleteAllByUserId(userId);
	}

	
	
}

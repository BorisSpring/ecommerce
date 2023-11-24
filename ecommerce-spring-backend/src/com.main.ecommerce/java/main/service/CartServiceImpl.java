package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Cart;
import main.domain.CartItem;
import main.domain.Product;
import main.domain.User;
import main.exception.*;
import main.repository.CartItemRepository;
import main.repository.CartRepository;
import main.requests.CreateCartItemRequest;
import main.requests.UpdateCartItemRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  CartService {

	private final CartRepository cartRepo;
	private final CartItemRepository cartItemRepo;
	private final UserService userService;
	private final ProductService productService;

	@Override
	public Cart findUserCart(String jwt) throws CartException {
		return null;
	}

	@Transactional
	@Override
	public void addCartItem(CreateCartItemRequest createCartItemRequest, String jwt) throws ProductException, CartException, CartItemException {
		User requestUser = userService.findUserFromJwt(jwt);
		Product requestProduct = productService.findProductById(createCartItemRequest.getProductId());

		for(CartItem cartItem: requestUser.getCart().getCartItems()){
			if(cartItem.getProduct().getId() == requestProduct.getId() && cartItem.getProduct().getSizes().equals(createCartItemRequest.getSize())){
				throw new CartItemException("Fail to add same item twice in cart!");
			}
		}


		AtomicBoolean isOnStock = new AtomicBoolean(false);
		requestProduct.getSizes().forEach(size -> {
			if(size.getSize().equals(createCartItemRequest.getSize()) && createCartItemRequest.getQuantity() <= size.getQuantity()){
				isOnStock.set(true);
			}
		});


		if(isOnStock.get() == false)
			throw new CartItemException("Fail to add item to cart  because it's out of stock!");


		AtomicBoolean isColorAvailable = new AtomicBoolean(false);
		requestProduct.getColors().forEach(color -> {
			if(color.equalsIgnoreCase(color)){
				isColorAvailable.set(true);
			}
		});

		if(!isColorAvailable.get())
			throw new CartItemException("Fail to add item to cart , there is no requested color!");


		cartItemRepo.save(CartItem.builder()
						.cart(requestUser.getCart())
						.quantity(createCartItemRequest.getQuantity())
						.totalPrice((requestProduct.getDiscountPrice() * createCartItemRequest.getQuantity()))
						.discountPrice(requestProduct.getDiscountPrice())
						.price(requestProduct.getPrice())
						.user(requestUser)
						.product(requestProduct)
						.size(createCartItemRequest.getSize())
						.discount((createCartItemRequest.getQuantity() * (requestProduct.getPrice() - requestProduct.getDiscountPrice())))
						.build());
	}

	@Transactional
	@Override
	public void updateCartItem(UpdateCartItemRequest updateCartItemRequest, String jwt) throws CartItemException, UserException, CartException {
		User requestUser = userService.findUserFromJwt(jwt);
		CartItem cartItem = cartItemRepo.findById(updateCartItemRequest.getCartItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Cart item with id " + updateCartItemRequest.getCartItemId() + " doesnt exists!"));

		if(cartItem.getUser().getId().equals(requestUser.getId()))
			throw new CartItemException("User is not related to this cart Item");

		cartItem.setQuantity(updateCartItemRequest.getQuantity());
		cartItem.setTotalPrice((cartItem.getDiscountPrice() * updateCartItemRequest.getQuantity()));
		cartItem.setDiscount( (updateCartItemRequest.getQuantity() - (cartItem.getPrice() - cartItem.getDiscountPrice()) ));
		cartItem.setSize(updateCartItemRequest.getSize());

		cartItemRepo.save(cartItem);
	}

	@Transactional
	@Override
	public void deleteCartItem(Integer cartItemId, String jwt) throws CartItemException {
		User requestUser = userService.findUserFromJwt(jwt);
		CartItem cartItem = findByCartItemById(cartItemId);

		if(cartItem.getUser().getId().equals(requestUser.getId())){
			cartItemRepo.deleteById(cartItemId);
		}else{
			throw new CartItemException("Fail to delete, User is not realted to this car item!");
		}
	}

	@Transactional
	@Override
	public void createCart(UUID userId) throws CartException {
		User requestUser = userService.findUserById(userId);

			if(requestUser.getCart() != null)
				throw  new CartException("User alerdy have cart");

			cartRepo.save(Cart.builder()
					.user(requestUser)
					.build());
	}

	@Override
	public CartItem findByCartItemById(int cartItemId) throws CartItemException {
		return cartItemRepo.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart item with id " + cartItemId + " doesnt exists!"));
	}

	@Override
	public boolean isCartItemExist(Product product, String size, UUID userId) {
		boolean isExist = cartItemRepo.existsByProductIdAndProductSizesSizeAndUserId(product.getId(), size, userId);
		System.out.println("exists: " + isExist);
		return  isExist;
	}

	
}

package main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import main.entity.Adress;
import main.entity.Cart;
import main.entity.CartItem;
import main.entity.MobileNumber;
import main.entity.Order;
import main.entity.OrderItem;
import main.entity.User;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.exception.OrderItemException;
import main.repository.AdressRepository;
import main.repository.MobileNumberRepository;
import main.repository.OrderItemRepository;
import main.repository.OrderRepository;
import main.repository.UserRepository;
import main.requests.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

	private CartService cartService;
	private OrderRepository orderRepo;
	private OrderItemRepository orderItemRepo;
	private AdressRepository adresRepo;
	private MobileNumberRepository mobileRepo;
	private UserRepository userRepo;

	
	public OrderServiceImpl(CartService cartService, OrderRepository orderRepo, OrderItemRepository orderItemRepo,
			AdressRepository adresRepo, MobileNumberRepository mobileRepo, UserRepository userRepo) {
		this.cartService = cartService;
		this.orderRepo = orderRepo;
		this.orderItemRepo = orderItemRepo;
		this.adresRepo = adresRepo;
		this.mobileRepo = mobileRepo;
		this.userRepo = userRepo;
	}

	@Override
	@Transactional
	public Order createOrder(User user, OrderRequest req) throws OrderException, CartItemException, CartException {
	
		
		
		Adress userAdress = adresRepo.findByAdressAndCityAndCountryAndPostalCodeAndStateAndUserId(req.getAdres().getAdress(), req.getAdres().getCity(), req.getAdres().getCountry(), req.getAdres().getPostalCode(), req.getAdres().getState(), user.getId());
			
			if(userAdress == null) {
				userAdress = new Adress();
				userAdress.setUser(user);
				userAdress = adresRepo.save(req.getAdres());
				user.getAdress().add(userAdress);
			}
			
			Cart cart = user.getCart();
			
			MobileNumber number = mobileRepo.findByNumber(req.getMobileNumber());
			
			
			
			if(number == null) {
				number = new MobileNumber();
				number.setUser(user);
				number.setNumber(req.getMobileNumber());
				MobileNumber savedMobileNumber = mobileRepo.save(number);
				if(savedMobileNumber == null) {
					throw new OrderException("Failed to save user number");
				}
			}else if(number.getUser().getId() != user.getId()) {
				throw new OrderException("Mobile number doesnt belong to this user!");
			}
			
			
			
			if(!cart.getCartItems().isEmpty()) {

				List<OrderItem> orderItems = new ArrayList<>();
				
				Order order = new Order();
				order.setMobileNumber(number);
				order.setUser(user);
				order.setAdress(userAdress);
				order.setCreatedAt(LocalDateTime.now());
				order.setDiscount(cart.getDiscount());
				order.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
				order.setTotalPrice(cart.getTotalPrice());
				order.setOrderStatus("CONFIRMED");
				order.setTotalItem(cart.getTotalQuantity());
				order.setOrderTime(LocalDateTime.now().plusDays(7));
				order.setFirstName(req.getFirstName());
				order.setLastName(req.getLastName());
				
				for(CartItem item: cart.getCartItems()) {
					OrderItem orderItem = new OrderItem();
					orderItem.setPrice(item.getPrice());
					orderItem.setDiscountPrice(item.getDiscountPrice());
					orderItem.setPrice(item.getPrice());
					orderItem.setProduct(item.getProduct());
					orderItem.setQuantity(item.getQuantity());
					orderItem.setSize(item.getSize());
					orderItem.setUserId(user.getId());
					orderItem.setOrder(order);
					orderItems.add(orderItem);
				}

				
				order.setOrderItems(orderItems);
				Order savedOrder = orderRepo.save(order);
				
				if(savedOrder == null) {
					throw new OrderException("Failed to place order!");
				}
				
				
				cart.setTotalQuantity(0);
				cart.setTotalDiscountPrice(0);
				cart.setTotalPrice(0);
				cart.setDiscount(0);
				userRepo.save(user);
				cartService.deleteAllByUserId(user.getId());
				
				return savedOrder;
			}
			
		throw new OrderException("U must have product to make order!");
	}



	@Override
	public Order findOrderyId(int orderId) throws OrderException {
		 Optional<Order> opt = orderRepo.findById(orderId);
		 
		 if(opt.isPresent()) {
			 return opt.get();
		 }
		 throw new OrderException("Order with id " + orderId + " not found");
	}

	@Override
	public List<Order> findUserOrders(int userId) {
		return orderRepo.findByUserId(userId);
	}


	@Transactional
	@Override
	public boolean confirmedOrder(int orderId) throws OrderException {
		 Order order = findOrderyId(orderId);
		 
		 order.setOrderStatus("CONFIRMED");
		 Order savedOrder = orderRepo.save(order);
		 
		 if(savedOrder == null) {
			 throw new OrderException("Failed to change order status!");
		 }
		 
		 return true;
	}
	
	@Transactional
	@Override
	public boolean shippedOrder(int orderId) throws OrderException {
		
		Order order = findOrderyId(orderId);
		 
		 order.setOrderStatus("SHIPPED");
		 Order savedOrder = orderRepo.save(order);
		 
		 if(savedOrder == null) {
			 throw new OrderException("Failed to change order status!");
		 }
		 
		 return true;
	}
	
	@Transactional
	@Override
	public boolean deliveredOrder(int orderId) throws OrderException {
		Order order = findOrderyId(orderId);
		 
		 order.setOrderStatus("DELIVERED");
		 order.setDeliveredTime(LocalDateTime.now());
		 Order savedOrder = orderRepo.save(order);
		 
		 if(savedOrder == null) {
			 throw new OrderException("Failed to change order status!");
		 }
		 
		 return true;
	}
	
	@Transactional
	@Override
	public boolean cancelOrder(int orderId) throws OrderException {
		
		 Order order = findOrderyId(orderId);
		 
		 order.setOrderStatus("CANCELED");
		 Order savedOrder = orderRepo.save(order);
		 
		 if(savedOrder == null) {
			 throw new OrderException("Failed to change order status!");
		 }
		 
		 return true;
	}

	@Override
	public Page<Order> getAllOrders(Integer pageNumber , Integer pageSize) {
			
		List<Order> allOrders = orderRepo.findAll();
	
			pageNumber-=1;
			
			int totalPages = (int) Math.ceil((double) allOrders.size()/pageSize);
			int beginIndex = pageNumber * pageSize;
			int endIndex;
			if((pageNumber + 1) == totalPages) {
				endIndex = allOrders.size();
			}else {
				endIndex = beginIndex + pageSize;
			}
			
			PageRequest pageable = PageRequest.of(pageNumber, pageSize);
			
			return new PageImpl<Order>(allOrders.subList(beginIndex, endIndex), pageable, allOrders.size());
	
	}

	@Transactional
	@Override
	public boolean deleteOrderAdmin(int orderId) throws OrderException {
		orderRepo.deleteById(orderId);
		return true;
	}
	

	@Transactional
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemException {
		 
		OrderItem savedOrderItem = orderItemRepo.save(orderItem);
		
		if(savedOrderItem == null) {
			throw new OrderItemException("Failed to save order item!");
		}
		return savedOrderItem;
	}

	@Override
	public Integer getSum() {
		System.out.println(orderRepo.getSum());
		 return orderRepo.getSum();
	}
	
	
}

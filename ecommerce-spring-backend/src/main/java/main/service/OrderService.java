package main.service;

import java.util.List;

import org.springframework.data.domain.Page;

import main.entity.Order;
import main.entity.OrderItem;
import main.entity.User;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.exception.OrderItemException;
import main.requests.OrderRequest;

public interface OrderService {

	public Order createOrder(User user, OrderRequest request) throws OrderException, CartItemException, CartException;
	
	public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemException;
		
	public Order findOrderyId(int orderId) throws OrderException;
	
	public List<Order> findUserOrders(int userId);
	
	public boolean confirmedOrder(int orderId) throws OrderException;
	
	public boolean shippedOrder(int orderId) throws OrderException;
	
	public boolean deliveredOrder(int orderId) throws OrderException;
	
	public boolean cancelOrder(int orderId) throws OrderException;
	
	public Page<Order> getAllOrders(Integer pageNumber, Integer pageSize);
	
	public boolean deleteOrderAdmin(int orderId) throws OrderException;

	public Integer getSum();
}

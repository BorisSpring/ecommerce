package main.service;

import java.util.List;
import org.springframework.data.domain.Page;
import main.domain.Order;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.requests.OrderRequest;
import org.springframework.data.domain.PageRequest;

public interface OrderService {

	void createOrder(String jwt, OrderRequest request) throws OrderException, CartItemException, CartException;


	Order findOrderById(int orderId) throws OrderException;

	List<Order> findUserOrders(String jwt);

	Page<Order> findAllOrders(PageRequest pageRequest);

	void deleteOrderAdmin(int orderId) throws OrderException;

	void returnedOrder(int orderId);

	void confirmedOrder(int orderId) throws OrderException;

	void shippedOrder(int orderId) throws OrderException;

	void deliveredOrder(int orderId) throws OrderException;

	void cancelOrder(int orderId, String jwt) throws OrderException;
}

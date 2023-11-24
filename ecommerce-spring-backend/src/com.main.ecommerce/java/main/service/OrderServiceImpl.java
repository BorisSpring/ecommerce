package main.service;

import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.*;
import main.exception.ResourceNotFoundException;
import main.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import main.exception.CartException;
import main.exception.CartItemException;
import main.exception.OrderException;
import main.requests.OrderRequest;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService  {

	private final OrderRepository orderRepository;
	private final AdressRepository adressRepository;
	private final MobileNumberRepository mobileRepo;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final TokenProvider tokenProvider;


    @Transactional
    @Override
    public void createOrder(String jwt, OrderRequest request) throws OrderException, CartItemException, CartException {
        User user = userService.findUserFromJwt(jwt);

        if(user.getCart() != null && user.getCart().getCartItems() != null && user.getCart().getCartItems().size() > 0){

            Adress adress = adressRepository.findByAdressIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCaseAndPostalCodeIgnoreCaseAndStateIgnoreCaseAndUserId
                    (request.getAdress(), request.getCity(), request.getCountry(), request.getPostalCode(), request.getState(), user.getId()).orElseGet(() -> {
                Adress newAdress = new Adress();
                newAdress.setUser(user);
                newAdress.setCity(request.getCity());
                newAdress.setPostalCode(request.getPostalCode());
                newAdress.setCountry(request.getCountry());
                newAdress.setAdress(request.getAdress());
                newAdress.setState(request.getState());
                return adressRepository.saveAndFlush(newAdress);
            });

            MobileNumber mobileNumber = mobileRepo.findByNumberAndUserId(request.getMobileNumber(), user.getId()).orElseGet(() -> {
                MobileNumber number = new MobileNumber();
                number.setUser(user);
                number.setNumber(request.getMobileNumber());
                return mobileRepo.saveAndFlush(number);
            });



            Order order = new Order();
            List<OrderItem> orderItems = new ArrayList<>();

            user.getCart().getCartItems().forEach(cartItem -> {
                    OrderItem orderItem = OrderItem.builder()
                                                .price(cartItem.getPrice())
                                                .product(cartItem.getProduct())
                                                .userId(user.getId())
                                                .discountPrice(cartItem.getDiscountPrice())
                                                .quantity(cartItem.getQuantity())
                                                .size(cartItem.getSize())
                                                .order(order)
                                                .build();
                    orderItems.add(orderItem);
            });


                order.setOrderItems(orderItems);
                order.setAdress(adress);
                order.setUser(user);
                order.setOrderStatus(OrderStatusEnum.NEW);
                order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getPrice).sum());
                order.setTotalDiscountedPrice(orderItems.stream().mapToDouble(OrderItem::getDiscountPrice).sum());
                order.setDiscount((order.getTotalPrice() - order.getTotalDiscountedPrice()));
                order.setTotalItem(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
                order.setFirstName(request.getFirstName());
                order.setLastName(request.getLastName());
                order.setMobileNumber(mobileNumber);

                orderRepository.saveAndFlush(order);
                cartItemRepository.deleteAllInBatch(user.getCart().getCartItems());
        }else{
            throw new OrderException("Fail to place order! U must have some items in the cart!");
        }
    }

    @Override
    public Order findOrderById(int orderId) throws OrderException {
       return  orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " doesnt exists!"));
    }

    @Override
    public List<Order> findUserOrders(String jwt) {
       return orderRepository.findByUserEmail(tokenProvider.getEmailFromToken(jwt));
    }

    @Override
    public Page<Order> findAllOrders(PageRequest pageRequest) {
        return  orderRepository.findAll(pageRequest);
    }

    @Transactional
    @Override
    public void deleteOrderAdmin(int orderId) throws OrderException {
        if(!orderRepository.existsById(orderId))
            throw new OrderException("Order with id " + orderId + "doesnt exists");
        orderRepository.deleteById(orderId);
    }

    @Transactional
    @Override
    public void returnedOrder(int orderId) {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatusEnum.RETURNED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void confirmedOrder(int orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatusEnum.CONFIRMED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void shippedOrder(int orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatusEnum.SHIPPED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void deliveredOrder(int orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatusEnum.DELIVERED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void cancelOrder(int orderId, String jwt) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatusEnum.CANCELED);
        orderRepository.save(order);
    }

}

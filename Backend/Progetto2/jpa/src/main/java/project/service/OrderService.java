package project.service;

import project.DTO.OrderDTO;
import project.DTO.OrderRequestDTO;
import project.exception.CartEmptyException;
import project.exception.OrderException;
import project.exception.UserNotFoundException;
import project.Mappers.OrderMapper;
import project.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.entities.Cart;
import project.entities.Order;
import project.entities.OrderItem;
import project.entities.User;
import project.repository.CartRepository;
import project.repository.OrderItemRepository;
import project.repository.OrderRepository;
import project.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        UserRepository userRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getOrder(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated())
            throw new RuntimeException("Non Autenticato");

        User user = (User) authentication.getPrincipal();

        List<Order> orders =
                orderRepository.findAllByUser_id(user.getId());

        for(Order order : orders) {
            updateOrderStatus(order);
        }

        return orderMapper.toDTOList(orders);
    }

    @Transactional
    public OrderDTO createOrder(long id, OrderRequestDTO orderRequestDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        Cart cart = user.getCart();

        if (cart.getItems().isEmpty())
            throw new CartEmptyException();

        Order ordine = Order.builder()
                .user(user)
                .productList(new ArrayList<>())
                .time(LocalDateTime.now())
                .totale(cart.getGrandTotal())
                .province(orderRequestDTO.getProvince())
                .address(orderRequestDTO.getAddress())
                .city(orderRequestDTO.getCity())
                .region(orderRequestDTO.getRegion())
                .orderStatus(OrderStatus.ORDINE_CONFERMATO)
                .build();

        orderRepository.save(ordine);

        for (OrderItem orderItem : cart.getItems()){
            orderItem.setOrder(ordine);
            orderItem.setCart(null);
            ordine.getProductList().add(orderItem);
            orderItemRepository.save(orderItem);
        }

        cart.setGrandTotal(BigDecimal.ZERO);
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDTO(ordine);
    }

    public void deleteOrder(long id, OrderDTO orderDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Iterator<Order> iterator = user.getListOrder().iterator();

        while (iterator.hasNext()) {

            Order order = iterator.next();

            if(order.getId() == orderDTO.getId()) {

                boolean cancellabile = order.getOrderStatus() == OrderStatus.ORDINE_CONFERMATO &&
                        order.getTime().plusMinutes(10).isAfter(LocalDateTime.now());

                if(!cancellabile) throw new OrderException();

                order.setOrderStatus(
                        OrderStatus.CANCELLATO
                );

                orderRepository.save(order);

                break;
            }
        }
    }

    public List<OrderDTO> findAllDTO(){
        return orderMapper.toDTOList(orderRepository.findAll());
    }

    public boolean pay(){
        Random random = new Random();
        int numeroCasuale = random.nextInt(10) + 1;
        return numeroCasuale <= 8;
    }

    public void updateOrderStatus(Order order) {

        if(order.getOrderStatus() == OrderStatus.CANCELLATO) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        if(order.getTime().plusHours(48).isBefore(now)) {

            order.setOrderStatus(OrderStatus.CONSEGNATO);

        } else if(order.getTime().plusMinutes(1).isBefore(now)) {

            order.setOrderStatus(OrderStatus.SPEDITO);

        } else {

            order.setOrderStatus(OrderStatus.ORDINE_CONFERMATO);
        }

        orderRepository.save(order);
    }
}
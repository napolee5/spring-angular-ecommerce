package project.service;

import project.DTO.CartDTO;
import project.DTO.OrderItemDTO;
import project.exception.CartNotFoundException;
import project.exception.UnauthorizedException;
import project.exception.UserNotFoundException;
import project.Mappers.CartMapper;
import project.Mappers.OrderItemMapper;
import project.entities.Cart;
import project.entities.OrderItem;
import project.entities.User;
import project.repository.CartRepository;
import project.repository.OrderItemRepository;
import project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       OrderItemRepository orderItemRepository,
                       OrderItemMapper orderItemMapper,
                       CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.cartMapper = cartMapper;
    }

    public Cart getCart(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) throw new UnauthorizedException();
        User user = (User) authentication.getPrincipal();
        return cartRepository.findByUser_id(user.getId()).orElseThrow(() -> new CartNotFoundException());
    }
    // ✅ NUOVO METODO DTO
    public CartDTO getCartDTO(Authentication authentication){
        return cartMapper.toDTO(getCart(authentication));
    }

    public int Arrotonda(int numero){
        if(numero <=10) return 10;
        else if(numero <=20) return 20;
        else if(numero <=30) return 30;
        else if(numero <=40) return 40;
        else return 50;
    }

    @Transactional
    public boolean add(Long id, OrderItemDTO orderItemDTO){

        User user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        Cart cart=cartRepository.findByUser_id(user.getId()).orElseThrow(() -> new CartNotFoundException());

        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);

        for(OrderItem orIt: cart.getItems()){
            if(orIt.getProduct().getId()==orderItem.getProduct().getId()){
                int newQuantity = orderItemDTO.getQuantity() + orIt.getQuantity();
                int Arrotondato=Arrotonda(newQuantity);
                orderItemDTO.setQuantity(Arrotondato);
                updateProduct(cart.getId(), orderItemDTO);
                return true;
            }
        }

        int Arrotondato=Arrotonda(orderItem.getQuantity());
        orderItem.setQuantity(Arrotondato);
        orderItem.setCart(cart);

        orderItemRepository.save(orderItem);
        cart.getItems().add(orderItem);

        BigDecimal newTotal=cart.getGrandTotal().add(orderItem.getPrice());
        cart.setGrandTotal(newTotal);

        cartRepository.save(cart);
        return true;
    }

    @Transactional
    public void deleteAll(Long UserId) {
        Cart removeCart = cartRepository.findByUser_id(UserId)
                .orElseThrow(() -> new CartNotFoundException());

        for (OrderItem orderItem : removeCart.getItems()) {
            orderItem.setCart(null);
            orderItemRepository.delete(orderItem);
        }

        removeCart.getItems().clear();
        removeCart.setGrandTotal(BigDecimal.ZERO);
        cartRepository.save(removeCart);
    }

    @Transactional
    public CartDTO deleteProduct(Long cartId, Long prodId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException());

        Iterator<OrderItem> iterator = cart.getItems().iterator();

        while (iterator.hasNext()) {
            OrderItem orderItem = iterator.next();
            if (orderItem.getProduct().getId() == prodId) {
                BigDecimal newPrice = cart.getGrandTotal().subtract(orderItem.getPrice());
                cart.setGrandTotal(newPrice);
                iterator.remove();
                break;
            }
        }

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Transactional
    public CartDTO updateProduct(Long cartId,OrderItemDTO orderItemDTO){
        Cart cart=cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());
        if(orderItemDTO.getQuantity()!=0) {
            for (OrderItem orderItem : cart.getItems()) {
                if (orderItem.getProduct().getId() == orderItemDTO.getProduct().getId()) {
                    BigDecimal prezzo = cart.getGrandTotal().subtract(orderItem.getPrice());
                    cart.setGrandTotal(prezzo.add(orderItemDTO.getPrice()));
                    orderItem.setQuantity(orderItemDTO.getQuantity());
                    orderItemRepository.save(orderItem);
                    cartRepository.save(cart);
                    break;
                }
            }
        }
        return cartMapper.toDTO(cart);
    }
}
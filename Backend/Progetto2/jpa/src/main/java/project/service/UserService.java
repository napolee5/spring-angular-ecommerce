package project.service;

import project.DTO.OrderDTO;
import project.DTO.OrderItemDTO;
import project.DTO.UserDTO;
import project.Mappers.OrderItemMapper;
import project.Mappers.OrderMapper;
import project.Mappers.UserMapper;
import project.exception.UserNotFoundException;
import project.entities.Order;
import project.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.repository.CartRepository;
import project.repository.OrderRepository;
import project.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public UserService(UserRepository userRepository,
                       CartRepository cartRepository,
                       OrderRepository orderRepository,
                       UserMapper userMapper,
                       OrderMapper orderMapper,
                       OrderItemMapper orderItemMapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Transactional
    public UserDTO getCredentials(Authentication authentication){
        if(authentication==null || !authentication.isAuthenticated())
            throw new RuntimeException("Non Autenticato");

        User principal = (User) authentication.getPrincipal();

        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new UserNotFoundException(principal.getEmail()));

        UserDTO dto = userMapper.toDTO(user);

        dto.setOrderList(
                user.getListOrder()
                        .stream()
                        .map(orderMapper::toDTO)
                        .collect(java.util.stream.Collectors.toSet())
        );

        return dto;
    }

    public List<Order> findOrderById(long id){
        return orderRepository.findAllByUser_id(id);
    }

    public Set<OrderDTO> findAllOrder(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(userDTO.getEmail()));

        return new HashSet<>(
                orderMapper.toDTOList(new ArrayList<>(user.getListOrder()))
        );
    }

    public List<OrderItemDTO> findCart(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException(userDTO.getEmail()));

        return orderItemMapper.toDTOList(user.getCart().getItems());
    }

    public UserDetails loadUserByUsername(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<UserDTO> findAllUsers(){
        List<UserDTO> listDTO = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            UserDTO dto = userMapper.toDTO(user);

            dto.setOrderList(
                    user.getListOrder()
                            .stream()
                            .map(orderMapper::toDTO)
                            .collect(java.util.stream.Collectors.toSet())
            );

            listDTO.add(dto);
        }

        return listDTO;
    }
}
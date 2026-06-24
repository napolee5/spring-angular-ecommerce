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
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       OrderMapper orderMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
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

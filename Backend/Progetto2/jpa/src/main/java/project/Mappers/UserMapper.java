package project.Mappers;

import project.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.DTO.UserDTO;
import project.DTO.UserOrderDTO;

@Mapper(componentModel = "spring", uses = CartMapper.class)
public interface UserMapper {

    @Mapping(target = "orderList", ignore = true)
    @Mapping(source = "role", target = "ruolo")
    UserDTO toDTO(User user);

    @Mapping(target = "listOrder", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(source = "ruolo", target = "role")
    User toEntity(UserDTO dto);

    UserOrderDTO toOrderDTO(User user);
}
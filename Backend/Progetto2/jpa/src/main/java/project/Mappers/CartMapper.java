package project.Mappers;

import project.DTO.CartDTO;
import project.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface CartMapper {

    CartDTO toDTO(Cart cart);

    @Mapping(target = "user", ignore = true)
    Cart toEntity(CartDTO dto);
}
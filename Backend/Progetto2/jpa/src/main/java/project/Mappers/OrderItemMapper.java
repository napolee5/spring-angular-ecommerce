package project.Mappers;

import project.DTO.OrderItemDTO;
import project.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderItemMapper {

    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "cart", ignore = true)
    OrderItem toEntity(OrderItemDTO dto);

    List<OrderItemDTO> toDTOList(List<OrderItem> items);
}
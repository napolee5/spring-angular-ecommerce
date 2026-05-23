package project.Mappers;

import project.DTO.OrderDTO;
import project.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(source = "productList", target = "orderItems")
    @Mapping(source = "totale", target = "total")
    @Mapping(source = "user", target = "user")
    OrderDTO toDTO(Order order);

    @Mapping(source = "orderItems", target = "productList")
    @Mapping(source = "total", target = "totale")
    @Mapping(target = "user", ignore = true)
    Order toEntity(OrderDTO dto);

    List<OrderDTO> toDTOList(List<Order> orders);

}
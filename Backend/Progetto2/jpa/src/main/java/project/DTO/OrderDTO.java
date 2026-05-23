package project.DTO;

import project.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {

    private long id;
    private UserOrderDTO user;
    private BigDecimal total;
    private LocalDateTime time;
    private String region;
    private String city;
    private String province;
    private String address;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;

}

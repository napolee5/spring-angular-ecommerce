package project.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO{
    private Long id;
    private List<OrderItemDTO> items;
    private BigDecimal grandTotal;
}

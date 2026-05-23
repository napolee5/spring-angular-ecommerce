package project.DTO;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    private ProductDTO product;
    private int quantity;

    public BigDecimal getPrice(){
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }
}



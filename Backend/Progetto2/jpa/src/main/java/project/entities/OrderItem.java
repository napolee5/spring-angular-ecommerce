package project.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cart")
    private Cart cart;

    @Column(nullable = false)
    private int quantity;

    public BigDecimal getPrice(){
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }
}

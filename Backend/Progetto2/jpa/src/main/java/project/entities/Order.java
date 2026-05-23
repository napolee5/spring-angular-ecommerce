package project.entities;

import project.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> productList;

    @Column(nullable = false)
    private BigDecimal totale;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

package project.entities;

import project.enums.ProdCategory;
import project.enums.SubCategory;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private ProdCategory category;

    @Enumerated(EnumType.STRING)
    private SubCategory subcategory;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

}

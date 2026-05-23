package project.DTO;

import project.enums.ProdCategory;
import project.enums.SubCategory;
import lombok.*;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

        private String name;
        private String image;
        private ProdCategory category;
        private SubCategory subcategory;
        private BigDecimal price;
        private String description;
}

package project.Mappers;
import org.mapstruct.Mapper;
import project.DTO.ProductDTO;
import project.DTO.ProductRequestDTO;
import project.entities.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntityFromDTO(ProductDTO dto);

    Product toEntityFromRequest(ProductRequestDTO dto);

    List<ProductDTO> toDTOList(List<Product> products);
}
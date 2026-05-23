package project.service;

import project.DTO.ProductDTO;
import project.DTO.ProductRequestDTO;
import project.exception.NameProductException;
import project.exception.ProductNotFoundException;
import project.Mappers.ProductMapper;
import project.entities.Product;
import project.enums.ProdCategory;
import project.enums.SubCategory;
import project.repository.ProductRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> searchByname(String name) {
        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(name);

        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOList(productRepository.findAll());
    }

    public List<ProductDTO> searchByCategory(ProdCategory category, @Nullable SubCategory subCategory) {
        List<Product> products;

        if (subCategory == null) {
            products = productRepository.findAllByCategory(category);
        } else {
            products = productRepository.findAllByCategoryAndSubcategory(category, subCategory);
        }

        return productMapper.toDTOList(products);
    }

    @Transactional
    public ProductDTO create(ProductRequestDTO prod) throws NameProductException {
        Product product = productMapper.toEntityFromRequest(prod);

        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new NameProductException();
        }

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public void delete(ProductDTO prod){
        Product product = productRepository.findById(prod.getId())
                .orElseThrow(() -> new ProductNotFoundException());

        productRepository.delete(product);
    }
}
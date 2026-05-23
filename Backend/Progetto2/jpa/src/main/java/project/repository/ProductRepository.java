package project.repository;

import project.entities.Product;
import project.enums.ProdCategory;
import project.enums.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findAllByCategory(ProdCategory category);
    List<Product> findAllByCategoryAndSubcategory(ProdCategory category, SubCategory sub);
}

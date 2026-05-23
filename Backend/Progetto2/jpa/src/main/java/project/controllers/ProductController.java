package project.controllers;

import project.DTO.ProductDTO;
import project.service.ProductService;
import project.enums.ProdCategory;
import project.enums.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> home(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search/byname")
    public ResponseEntity<List<ProductDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(
                productService.searchByname(name)
        );
    }

    @GetMapping("/search/bycategory")
    public ResponseEntity<List<ProductDTO>> searchByCategory(
            @RequestParam ProdCategory category,
            @RequestParam(required = false)
            SubCategory subCategory) {

        return ResponseEntity.ok(
                productService.searchByCategory(
                        category,
                        subCategory
                )
        );
    }
}
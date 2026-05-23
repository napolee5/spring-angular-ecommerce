package project.controllers;

import project.DTO.OrderDTO;
import project.DTO.ProductDTO;
import project.DTO.ProductRequestDTO;
import project.DTO.UserDTO;
import project.Mappers.OrderMapper;
import project.service.OrderService;
import project.service.ProductService;
import project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper; // ✅ AGGIUNTO

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductRequestDTO productRequestDTO) {

        ProductDTO product =
                productService.create(productRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    @GetMapping("/allOrder")
    public ResponseEntity<List<OrderDTO>> allOrder(){
        return ResponseEntity.ok(orderService.findAllDTO());
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> allUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(
            @RequestBody ProductDTO productDTO) {

        productService.delete(productDTO);

        return ResponseEntity.noContent().build();
    }
}
package project.controllers;

import project.service.CartService;
import project.service.OrderService;
import project.service.UserService;
import project.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.DTO.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/profile/myCart")
    public ResponseEntity<CartDTO> getMyCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCartDTO(authentication));
    }

    @GetMapping("/profile/myOrder")
    public ResponseEntity<List<OrderDTO>> getMyOrder(Authentication authentication){
        return ResponseEntity.ok(orderService.getOrder(authentication));
    }

    @GetMapping("/profile/myCredential")
    public ResponseEntity<UserDTO> getMyCredentials(Authentication authentication){
        return ResponseEntity.ok(userService.getCredentials(authentication));
    }

    @PostMapping("cart/add")
    public ResponseEntity<?> add(@RequestBody OrderItemDTO orderItemDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        cartService.add(user.getId(), orderItemDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("cart/edit")
    public ResponseEntity<CartDTO> edit(@RequestBody OrderItemDTO orderItemDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.updateProduct(user.getId(), orderItemDTO));
    }

    @DeleteMapping("cart/deleteProd")
    public ResponseEntity<CartDTO> deleteProd(@RequestParam Long ProdID, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cartService.deleteProduct(user.getId(), ProdID));
    }

    @DeleteMapping("cart/deleteAll")
    public ResponseEntity<?> deleteAll(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        cartService.deleteAll(user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("order/deleteOrder")
    public ResponseEntity<?> deleteOrder(Authentication authentication, @RequestBody OrderDTO orderDTO){
        User user = (User) authentication.getPrincipal();
        orderService.deleteOrder(user.getId(), orderDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("checkout/pay")
    public ResponseEntity<?> payment(Authentication authentication, @RequestBody OrderRequestDTO orderRequestDTO){
        User user = (User) authentication.getPrincipal();
        if(!orderService.pay()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pagamento rifiutato");
        }
        return ResponseEntity.ok(orderService.createOrder(user.getId(), orderRequestDTO));
    }

}
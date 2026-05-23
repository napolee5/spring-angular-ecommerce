package project.DTO;

import project.enums.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private Role ruolo;
    private Set<OrderDTO> orderList;
    private CartDTO cart;
}

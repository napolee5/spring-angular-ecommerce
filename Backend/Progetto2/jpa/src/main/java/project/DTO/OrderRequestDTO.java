package project.DTO;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDTO {
    private String region;
    private String city;
    private String province;
    private String address;
}

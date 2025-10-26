package model.dto.hamburger;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HamburgerResponse {
    private Long id;
    private String name;
    private String breadName;
    private String meatName;
    private String cheeseName;
    private String toppings; // Comma-separated
    private String condiments; // Comma-separated
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
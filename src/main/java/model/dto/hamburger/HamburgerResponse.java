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
    private String meatAmountName;
    private String cheeseName;
    private String toppings;
    private String condiments;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
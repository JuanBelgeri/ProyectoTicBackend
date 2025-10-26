package model.dto.pizza;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PizzaResponse {
    private Long id;
    private String name;
    private String sizeName;
    private String doughName;
    private String sauceName;
    private String cheeseName;
    private String toppings; // Comma-separated
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}

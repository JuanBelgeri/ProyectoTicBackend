package model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ComponentResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean active;
}
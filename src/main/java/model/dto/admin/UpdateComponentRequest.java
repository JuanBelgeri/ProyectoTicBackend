package model.dto.admin;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateComponentRequest {
    private String name;
    private BigDecimal price;
    private Boolean active;
}
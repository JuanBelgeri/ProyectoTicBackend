package model.dto.admin;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateComponentRequest {
    private String name;
    private BigDecimal price;
}
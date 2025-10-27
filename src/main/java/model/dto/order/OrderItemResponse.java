package model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String itemType;
    private String itemName;
    private String itemDescription;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
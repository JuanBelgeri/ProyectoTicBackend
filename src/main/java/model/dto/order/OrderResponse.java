package model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String userEmail;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String notes;
    private List<OrderItemResponse> items;
    private Integer totalItems;
}
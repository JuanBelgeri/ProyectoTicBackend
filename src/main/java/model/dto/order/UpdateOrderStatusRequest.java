package model.dto.order;

import lombok.Data;
import model.enums.OrderStatus;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
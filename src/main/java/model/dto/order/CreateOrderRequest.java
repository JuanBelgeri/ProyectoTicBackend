package model.dto.order;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long addressId;
    private Long paymentMethodId;
    private String notes;
}
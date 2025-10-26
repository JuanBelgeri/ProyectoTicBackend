package model.dto.cart;

import lombok.Data;

@Data
public class AddToCartRequest {
    private String itemType; // "PIZZA" or "HAMBURGER"
    private Long itemId;
    private Integer quantity;
}
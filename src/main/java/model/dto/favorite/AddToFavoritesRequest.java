package model.dto.favorite;

import lombok.Data;

@Data
public class AddToFavoritesRequest {
    private String itemType; // "PIZZA" or "HAMBURGER"
    private Long itemId;
    private String customName;
}
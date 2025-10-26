package model.dto.favorite;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FavoriteResponse {
    private Long id;
    private String itemType;
    private Long itemId;
    private String customName;
    private LocalDateTime addedAt;
}
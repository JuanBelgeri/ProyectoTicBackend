package model.dto.pizza;

import lombok.Data;
import java.util.List;

@Data
public class CreatePizzaRequest {
    private String name;
    private Long sizeId;
    private Long doughId;
    private Long sauceId;
    private Long cheeseId;
    private List<Long> toppingIds;
}

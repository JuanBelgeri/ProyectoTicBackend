package model.dto.hamburger;

import lombok.Data;
import java.util.List;

@Data
public class CreateHamburgerRequest {
    private String name;
    private Long breadId;
    private Long meatId;
    private Long cheeseId;
    private List<Long> toppingIds;
    private List<Long> condimentIds;
}
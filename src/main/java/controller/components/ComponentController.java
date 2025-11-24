package controller.components;

import model.dto.ApiResponse;
import model.components.*;
import service.components.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@CrossOrigin(origins = "*")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // Get all pizza sizes
    @GetMapping("/pizza-sizes")
    public ResponseEntity<ApiResponse> getPizzaSizes() {
        List<PizzaSize> sizes = componentService.getAllActivePizzaSizes();
        return ResponseEntity.ok(new ApiResponse(true, "Pizza sizes retrieved", sizes));
    }

    // Get all dough types
    @GetMapping("/dough-types")
    public ResponseEntity<ApiResponse> getDoughTypes() {
        List<DoughType> doughs = componentService.getAllActiveDoughTypes();
        return ResponseEntity.ok(new ApiResponse(true, "Dough types retrieved", doughs));
    }

    // Get all sauce types
    @GetMapping("/sauce-types")
    public ResponseEntity<ApiResponse> getSauceTypes() {
        List<SauceType> sauces = componentService.getAllActiveSauceTypes();
        return ResponseEntity.ok(new ApiResponse(true, "Sauce types retrieved", sauces));
    }

    // Get all cheese types
    @GetMapping("/cheese-types")
    public ResponseEntity<ApiResponse> getCheeseTypes() {
        List<CheeseType> cheeses = componentService.getAllActiveCheeseTypes();
        return ResponseEntity.ok(new ApiResponse(true, "Cheese types retrieved", cheeses));
    }

    // Get all bread types
    @GetMapping("/bread-types")
    public ResponseEntity<ApiResponse> getBreadTypes() {
        List<BreadType> breads = componentService.getAllActiveBreadTypes();
        return ResponseEntity.ok(new ApiResponse(true, "Bread types retrieved", breads));
    }

    // Get all meat types
    @GetMapping("/meat-types")
    public ResponseEntity<ApiResponse> getMeatTypes() {
        List<MeatType> meats = componentService.getAllActiveMeatTypes();
        return ResponseEntity.ok(new ApiResponse(true, "Meat types retrieved", meats));
    }

    // Get all meat amounts
    @GetMapping("/meat-amounts")
    public ResponseEntity<ApiResponse> getMeatAmounts() {
        List<MeatAmount> amounts = componentService.getAllActiveMeatAmounts();
        return ResponseEntity.ok(new ApiResponse(true, "Meat amounts retrieved", amounts));
    }

    // Get all toppings
    @GetMapping("/toppings")
    public ResponseEntity<ApiResponse> getToppings() {
        List<Topping> toppings = componentService.getAllActiveToppings();
        return ResponseEntity.ok(new ApiResponse(true, "Toppings retrieved", toppings));
    }

    // Get all condiments
    @GetMapping("/condiments")
    public ResponseEntity<ApiResponse> getCondiments() {
        List<Condiment> condiments = componentService.getAllActiveCondiments();
        return ResponseEntity.ok(new ApiResponse(true, "Condiments retrieved", condiments));
    }

    // Get all components at once (for frontend initialization)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllComponents() {
        var components = new java.util.HashMap<String, Object>();
        components.put("pizzaSizes", componentService.getAllActivePizzaSizes());
        components.put("doughTypes", componentService.getAllActiveDoughTypes());
        components.put("sauceTypes", componentService.getAllActiveSauceTypes());
        components.put("cheeseTypes", componentService.getAllActiveCheeseTypes());
        components.put("breadTypes", componentService.getAllActiveBreadTypes());
        components.put("meatTypes", componentService.getAllActiveMeatTypes());
        components.put("meatAmounts", componentService.getAllActiveMeatAmounts());
        components.put("toppings", componentService.getAllActiveToppings());
        components.put("condiments", componentService.getAllActiveCondiments());

        return ResponseEntity.ok(new ApiResponse(true, "All components retrieved", components));
    }
}
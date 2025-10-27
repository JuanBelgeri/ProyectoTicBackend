package controller.builds;

import model.dto.ApiResponse;
import model.dto.pizza.*;
import model.builds.Pizza;
import model.components.*;
import service.builds.PizzaService;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin(origins = "*")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    private DoughTypeRepository doughTypeRepository;

    @Autowired
    private SauceTypeRepository sauceTypeRepository;

    @Autowired
    private CheeseTypeRepository cheeseTypeRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    // Create pizza
    @PostMapping
    public ResponseEntity<ApiResponse> createPizza(
            @RequestParam String userEmail,
            @RequestBody CreatePizzaRequest request) {
        try {
            // Get components
            PizzaSize size = pizzaSizeRepository.findById(request.getSizeId())
                    .orElseThrow(() -> new IllegalArgumentException("Pizza size not found"));
            DoughType dough = doughTypeRepository.findById(request.getDoughId())
                    .orElseThrow(() -> new IllegalArgumentException("Dough type not found"));

            // Create pizza
            Pizza pizza = new Pizza(userEmail, request.getName(), size, dough);

            // Add optional components
            if (request.getSauceId() != null) {
                SauceType sauce = sauceTypeRepository.findById(request.getSauceId())
                        .orElseThrow(() -> new IllegalArgumentException("Sauce type not found"));
                pizza.setSauce(sauce);
            }

            if (request.getCheeseId() != null) {
                CheeseType cheese = cheeseTypeRepository.findById(request.getCheeseId())
                        .orElseThrow(() -> new IllegalArgumentException("Cheese type not found"));
                pizza.setCheese(cheese);
            }

            // Add toppings
            if (request.getToppingIds() != null && !request.getToppingIds().isEmpty()) {
                for (Long toppingId : request.getToppingIds()) {
                    Topping topping = toppingRepository.findById(toppingId)
                            .orElseThrow(() -> new IllegalArgumentException("Topping not found: " + toppingId));
                    pizza.addTopping(topping);
                }
            }

            // Save pizza
            Pizza savedPizza = pizzaService.createPizza(pizza);

            // Create response
            PizzaResponse response = mapToPizzaResponse(savedPizza);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Pizza created successfully", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create pizza: " + e.getMessage()));
        }
    }

    // Get all pizzas for user
    @GetMapping
    public ResponseEntity<ApiResponse> getUserPizzas(@RequestParam String userEmail) {
        List<Pizza> pizzas = pizzaService.getPizzasByUser(userEmail);
        List<PizzaResponse> responses = pizzas.stream()
                .map(this::mapToPizzaResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(true, "Pizzas retrieved", responses));
    }

    // Get pizza by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id)
                .map(pizza -> ResponseEntity.ok(
                        new ApiResponse(true, "Pizza found", mapToPizzaResponse(pizza))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Pizza not found")));
    }

    // Delete pizza
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePizza(@PathVariable Long id) {
        try {
            pizzaService.deletePizza(id);
            return ResponseEntity.ok(new ApiResponse(true, "Pizza deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete pizza"));
        }
    }

    // Helper method to map Pizza to PizzaResponse
    private PizzaResponse mapToPizzaResponse(Pizza pizza) {
        String toppingsStr = pizza.getToppings().stream()
                .map(Topping::getName)
                .collect(Collectors.joining(", "));

        return new PizzaResponse(
                pizza.getId(),
                pizza.getName(),
                pizza.getSize().getName(),
                pizza.getDough().getName(),
                pizza.getSauce() != null ? pizza.getSauce().getName() : "None",
                pizza.getCheese() != null ? pizza.getCheese().getName() : "None",
                toppingsStr.isEmpty() ? "None" : toppingsStr,
                pizza.getTotalPrice(),
                pizza.getCreatedAt()
        );
    }
}
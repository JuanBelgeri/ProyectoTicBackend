package controller.builds;

import model.dto.ApiResponse;
import model.dto.hamburger.*;
import model.builds.Hamburger;
import model.components.*;
import service.builds.HamburgerService;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hamburgers")
@CrossOrigin(origins = "*")
public class HamburgerController {

        @Autowired
        private HamburgerService hamburgerService;

        @Autowired
        private BreadTypeRepository breadTypeRepository;

        @Autowired
        private MeatTypeRepository meatTypeRepository;

        @Autowired
        private MeatAmountRepository meatAmountRepository;

        @Autowired
        private CheeseTypeRepository cheeseTypeRepository;

        @Autowired
        private ToppingRepository toppingRepository;

        @Autowired
        private CondimentRepository condimentRepository;

        // Create hamburger
        @PostMapping
        public ResponseEntity<ApiResponse> createHamburger(
                        @RequestParam String userEmail,
                        @RequestBody CreateHamburgerRequest request) {
                try {
                        // Get components
                        BreadType bread = breadTypeRepository.findById(request.getBreadId())
                                        .orElseThrow(() -> new IllegalArgumentException("Bread type not found"));
                        MeatType meat = meatTypeRepository.findById(request.getMeatId())
                                        .orElseThrow(() -> new IllegalArgumentException("Meat type not found"));
                        MeatAmount meatAmount = meatAmountRepository.findById(request.getMeatAmountId())
                                        .orElseThrow(() -> new IllegalArgumentException("Meat amount not found"));

                        // Create hamburger
                        Hamburger hamburger = new Hamburger(userEmail, request.getName(), bread, meat, meatAmount);

                        // Add cheeses
                        if (request.getCheeseIds() != null && !request.getCheeseIds().isEmpty()) {
                                for (Long cheeseId : request.getCheeseIds()) {
                                        CheeseType cheese = cheeseTypeRepository.findById(cheeseId)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                        "Cheese type not found"));
                                        hamburger.addCheese(cheese);
                                }
                        }
                        // Add toppings
                        if (request.getToppingIds() != null && !request.getToppingIds().isEmpty()) {
                                for (Long toppingId : request.getToppingIds()) {
                                        Topping topping = toppingRepository.findById(toppingId)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                        "Topping not found"));
                                        hamburger.addTopping(topping);
                                }
                        }

                        // Add condiments
                        if (request.getCondimentIds() != null && !request.getCondimentIds().isEmpty()) {
                                for (Long condimentId : request.getCondimentIds()) {
                                        Condiment condiment = condimentRepository.findById(condimentId)
                                                        .orElseThrow(() -> new IllegalArgumentException(
                                                                        "Condiment not found"));
                                        hamburger.addCondiment(condiment);
                                }
                        }

                        // Recalculate total price to ensure all components are included
                        hamburger.calculateTotalPrice();

                        // Save hamburger
                        Hamburger savedHamburger = hamburgerService.createHamburger(hamburger);

                        // Create response
                        HamburgerResponse response = mapToHamburgerResponse(savedHamburger);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(new ApiResponse(true, "Hamburger created successfully", response));

                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new ApiResponse(false, e.getMessage()));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(new ApiResponse(false, "Failed to create hamburger: " + e.getMessage()));
                }
        }

        // Get all hamburgers for user
        @GetMapping
        public ResponseEntity<ApiResponse> getUserHamburgers(@RequestParam String userEmail) {
                List<Hamburger> hamburgers = hamburgerService.getHamburgersByUser(userEmail);
                List<HamburgerResponse> responses = hamburgers.stream()
                                .map(this::mapToHamburgerResponse)
                                .collect(Collectors.toList());
                return ResponseEntity.ok(new ApiResponse(true, "Hamburgers retrieved", responses));
        }

        // Get hamburger by id
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse> getHamburgerById(@PathVariable Long id) {
                return hamburgerService.getHamburgerById(id)
                                .map(hamburger -> ResponseEntity.ok(
                                                new ApiResponse(true, "Hamburger found",
                                                                mapToHamburgerResponse(hamburger))))
                                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(new ApiResponse(false, "Hamburger not found")));
        }

        // Delete hamburger
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse> deleteHamburger(@PathVariable Long id) {
                try {
                        hamburgerService.deleteHamburger(id);
                        return ResponseEntity.ok(new ApiResponse(true, "Hamburger deleted"));
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(new ApiResponse(false, "Failed to delete hamburger"));
                }
        }

        // Helper method
        private HamburgerResponse mapToHamburgerResponse(Hamburger hamburger) {
                String toppingsStr = hamburger.getToppings().stream()
                                .map(Topping::getName)
                                .collect(Collectors.joining(", "));

                String condimentsStr = hamburger.getCondiments().stream()
                                .map(Condiment::getName)
                                .collect(Collectors.joining(", "));

                String cheesesStr = hamburger.getCheeses().stream()
                                .map(CheeseType::getName)
                                .collect(Collectors.joining(", "));

                return new HamburgerResponse(
                                hamburger.getId(),
                                hamburger.getName(),
                                hamburger.getBread().getName(),
                                hamburger.getMeat().getName(),
                                hamburger.getMeatAmount().getName(),
                                cheesesStr.isEmpty() ? "None" : cheesesStr,
                                toppingsStr.isEmpty() ? "None" : toppingsStr,
                                condimentsStr.isEmpty() ? "None" : condimentsStr,
                                hamburger.getTotalPrice(),
                                hamburger.getCreatedAt());
        }
}
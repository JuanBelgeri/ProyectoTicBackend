package controller.integration;

import service.security.CardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class CardValidationController {

    @Autowired
    private CardValidationService cardValidationService;

    @PostMapping("/validate-card")
    public ResponseEntity<Map<String, Object>> validateCard(@RequestBody Map<String, String> request) {
        try {
            String cardNumber = request.get("cardNumber");

            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("valid", false);
                errorResponse.put("error", "Card number is required");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            Map<String, Object> validationResult = cardValidationService.validateCard(cardNumber);

            if ((Boolean) validationResult.getOrDefault("valid", false)) {
                return ResponseEntity.ok(validationResult);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validationResult);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("valid", false);
            errorResponse.put("error", "Card validation failed: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Card Validation API");
        response.put("timestamp", java.time.LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}
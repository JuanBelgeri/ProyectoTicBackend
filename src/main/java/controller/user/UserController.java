package controller.user;

import model.dto.ApiResponse;
import model.users.Address;
import model.users.PaymentMethod;
import service.users.AddressService;
import service.users.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    // ============ ADDRESSES ============

    // Get all addresses for user
    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse> getUserAddresses(@RequestParam String userEmail) {
        try {
            List<Address> addresses = addressService.getAddressesByUser(userEmail);
            return ResponseEntity.ok(new ApiResponse(true, "Addresses retrieved", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get addresses: " + e.getMessage()));
        }
    }

    // Get main address for user
    @GetMapping("/addresses/main")
    public ResponseEntity<ApiResponse> getMainAddress(@RequestParam String userEmail) {
        try {
            return addressService.getMainAddress(userEmail)
                    .map(address -> ResponseEntity.ok(new ApiResponse(true, "Main address found", address)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse(false, "No main address found")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get main address: " + e.getMessage()));
        }
    }

    // ============ PAYMENT METHODS ============

    // Get all payment methods for user
    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> getUserPaymentMethods(@RequestParam String userEmail) {
        try {
            List<PaymentMethod> paymentMethods = paymentMethodService.getPaymentMethodsByUser(userEmail);
            // Return payment methods (card numbers are already encrypted in database)
            return ResponseEntity.ok(new ApiResponse(true, "Payment methods retrieved", paymentMethods));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get payment methods: " + e.getMessage()));
        }
    }

    // Get main payment method for user
    @GetMapping("/payments/main")
    public ResponseEntity<ApiResponse> getMainPaymentMethod(@RequestParam String userEmail) {
        try {
            return paymentMethodService.getMainPaymentMethod(userEmail)
                    .map(pm -> ResponseEntity.ok(new ApiResponse(true, "Main payment method found", pm)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse(false, "No main payment method found")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get main payment method: " + e.getMessage()));
        }
    }
}


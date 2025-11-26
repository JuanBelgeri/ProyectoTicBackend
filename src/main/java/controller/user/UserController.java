package controller.user;

import model.dto.ApiResponse;
import model.dto.user.CreateAddressRequest;
import model.dto.user.CreatePaymentMethodRequest;
import model.dto.user.UpdateAddressRequest;
import model.dto.user.UpdatePaymentMethodRequest;
import model.users.Address;
import model.users.PaymentMethod;
import service.users.AddressService;
import service.users.PaymentMethodService;
import service.security.CardValidationService;
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

    @Autowired
    private CardValidationService cardValidationService;

    // ADDRESSES

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

    // Create new address
    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse> createAddress(
            @RequestParam String userEmail,
            @RequestBody CreateAddressRequest request) {
        try {
            if (request.getStreet() == null || request.getStreet().trim().isEmpty() ||
                    request.getNumber() == null || request.getNumber().trim().isEmpty() ||
                    request.getCity() == null || request.getCity().trim().isEmpty() ||
                    request.getPostalCode() == null || request.getPostalCode().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Street, number, city, and postal code are required"));
            }

            Address address = new Address(
                    userEmail,
                    request.getStreet(),
                    request.getNumber(),
                    request.getCity(),
                    request.getPostalCode(),
                    request.getIsMain() != null ? request.getIsMain() : false
            );
            address.setApartment(request.getApartment());
            address.setAdditionalInfo(request.getAdditionalInfo());

            Address savedAddress = addressService.createAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Address created successfully", savedAddress));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create address: " + e.getMessage()));
        }
    }

    // Update address
    @PutMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse> updateAddress(
            @PathVariable Long id,
            @RequestParam String userEmail,
            @RequestBody UpdateAddressRequest request) {
        try {
            Address address = addressService.getAddressesByUser(userEmail).stream()
                    .filter(addr -> addr.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Address not found"));

            if (!address.getUserEmail().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Address does not belong to user"));
            }

            // Update fields
            if (request.getStreet() != null) address.setStreet(request.getStreet());
            if (request.getNumber() != null) address.setNumber(request.getNumber());
            if (request.getApartment() != null) address.setApartment(request.getApartment());
            if (request.getCity() != null) address.setCity(request.getCity());
            if (request.getPostalCode() != null) address.setPostalCode(request.getPostalCode());
            if (request.getAdditionalInfo() != null) address.setAdditionalInfo(request.getAdditionalInfo());
            if (request.getIsMain() != null) address.setIsMain(request.getIsMain());

            Address updatedAddress = addressService.updateAddress(id, address);
            return ResponseEntity.ok(new ApiResponse(true, "Address updated successfully", updatedAddress));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update address: " + e.getMessage()));
        }
    }

    // Delete address
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(
            @PathVariable Long id,
            @RequestParam String userEmail) {
        try {
            Address address = addressService.getAddressesByUser(userEmail).stream()
                    .filter(addr -> addr.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Address not found"));

            if (!address.getUserEmail().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Address does not belong to user"));
            }

            addressService.deleteAddress(id);
            return ResponseEntity.ok(new ApiResponse(true, "Address deleted successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete address: " + e.getMessage()));
        }
    }

    // PAYMENT METHODS

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

    // Create new payment method
    @PostMapping("/payments")
    public ResponseEntity<ApiResponse> createPaymentMethod(
            @RequestParam String userEmail,
            @RequestBody CreatePaymentMethodRequest request) {
        try {
            if (request.getType() == null || request.getType().trim().isEmpty() ||
                    request.getCardNumber() == null || request.getCardNumber().trim().isEmpty() ||
                    request.getCardHolder() == null || request.getCardHolder().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Type, card number, and card holder are required"));
            }

            // Validate card number using Luhn algorithm
            String cleanCardNumber = request.getCardNumber().replaceAll("[\\s-]", "");
            if (!cardValidationService.isValidCardNumber(cleanCardNumber)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid card number"));
            }

            // Encrypt card number
            String encryptedCardNumber = cardValidationService.encryptCardNumber(cleanCardNumber);

            PaymentMethod paymentMethod = new PaymentMethod(
                    userEmail,
                    request.getType().toUpperCase(),
                    encryptedCardNumber,
                    request.getCardHolder(),
                    request.getExpirationDate(),
                    request.getIsMain() != null ? request.getIsMain() : false
            );

            PaymentMethod savedPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethod);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Payment method created successfully", savedPaymentMethod));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create payment method: " + e.getMessage()));
        }
    }

    // Update payment method
    @PutMapping("/payments/{id}")
    public ResponseEntity<ApiResponse> updatePaymentMethod(
            @PathVariable Long id,
            @RequestParam String userEmail,
            @RequestBody UpdatePaymentMethodRequest request) {
        try {
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodsByUser(userEmail).stream()
                    .filter(pm -> pm.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Payment method not found"));

            if (!paymentMethod.getUserEmail().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Payment method does not belong to user"));
            }

            // Update fields
            if (request.getType() != null) paymentMethod.setType(request.getType().toUpperCase());
            if (request.getCardHolder() != null) paymentMethod.setCardHolder(request.getCardHolder());
            if (request.getExpirationDate() != null) paymentMethod.setExpirationDate(request.getExpirationDate());
            if (request.getIsMain() != null) paymentMethod.setIsMain(request.getIsMain());

            // Update card number if provided
            if (request.getCardNumber() != null && !request.getCardNumber().trim().isEmpty()) {
                String cleanCardNumber = request.getCardNumber().replaceAll("[\\s-]", "");
                if (!cardValidationService.isValidCardNumber(cleanCardNumber)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "Invalid card number"));
                }
                String encryptedCardNumber = cardValidationService.encryptCardNumber(cleanCardNumber);
                paymentMethod.setCardNumberEncrypted(encryptedCardNumber);
            }

            PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethod);
            return ResponseEntity.ok(new ApiResponse(true, "Payment method updated successfully", updatedPaymentMethod));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update payment method: " + e.getMessage()));
        }
    }

    // Delete payment method
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<ApiResponse> deletePaymentMethod(
            @PathVariable Long id,
            @RequestParam String userEmail) {
        try {
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodsByUser(userEmail).stream()
                    .filter(pm -> pm.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Payment method not found"));

            if (!paymentMethod.getUserEmail().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Payment method does not belong to user"));
            }

            paymentMethodService.deletePaymentMethod(id);
            return ResponseEntity.ok(new ApiResponse(true, "Payment method deleted successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete payment method: " + e.getMessage()));
        }
    }
}


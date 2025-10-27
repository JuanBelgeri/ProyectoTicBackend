package controller.auth;

import model.dto.auth.*;
import model.dto.ApiResponse;
import model.users.User;
import model.users.Address;
import model.users.PaymentMethod;
import model.enums.UserRole;
import service.users.UserService;
import service.users.AddressService;
import service.users.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    // Register new CLIENT
    @PostMapping("/register/client")
    public ResponseEntity<ApiResponse> registerClient(@RequestBody RegisterClientRequest request) {
        try {
            // Check if user already exists
            if (userService.userExists(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(false, "Email already registered"));
            }

            // Create new user
            User user = new User(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDocument(),
                    request.getPhone(),
                    request.getBirthDate(),
                    UserRole.CLIENT,
                    request.getPassword()
                    // TTODO: Encrypt password with BCrypt
            );

            User savedUser = userService.createUser(user);

            // Create address (required)
            Address address = new Address(
                    savedUser.getEmail(),
                    request.getStreet(),
                    request.getNumber(),
                    request.getCity(),
                    request.getPostalCode(),
                    true // First address is always main
            );
            address.setApartment(request.getApartment());
            address.setAdditionalInfo(request.getAddressAdditionalInfo());
            addressService.createAddress(address);

            // Create payment method (required for clients)
            PaymentMethod paymentMethod = new PaymentMethod(
                    savedUser.getEmail(),
                    request.getPaymentType(),
                    encryptCardNumber(request.getCardNumber()), // Encrypt card number
                    request.getCardHolder(),
                    request.getExpirationDate(),
                    true // First payment method is always main
            );
            paymentMethodService.createPaymentMethod(paymentMethod);

            AuthResponse authResponse = new AuthResponse(
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole(),
                    "Client registered successfully"
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Registration successful", authResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    // Register new ADMIN
    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse> registerAdmin(@RequestBody RegisterAdminRequest request) {
        try {
            // Check if user already exists
            if (userService.userExists(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(false, "Email already registered"));
            }

            // Create new admin user
            User user = new User(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDocument(),
                    request.getPhone(),
                    request.getBirthDate(),
                    UserRole.ADMIN,
                    request.getPassword() // TTODO: Encrypt password with BCrypt
            );

            User savedUser = userService.createUser(user);

            // Create address (required)
            Address address = new Address(
                    savedUser.getEmail(),
                    request.getStreet(),
                    request.getNumber(),
                    request.getCity(),
                    request.getPostalCode(),
                    true // First address is always main
            );
            address.setApartment(request.getApartment());
            address.setAdditionalInfo(request.getAddressAdditionalInfo());
            addressService.createAddress(address);

            // Note: No payment method for admins

            AuthResponse authResponse = new AuthResponse(
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole(),
                    "Admin registered successfully"
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Registration successful", authResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            // Validate credentials
            if (!userService.validateCredentials(request.getEmail(), request.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Invalid email or password"));
            }

            // Get user
            Optional<User> userOpt = userService.getUserByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "User not found"));
            }

            User user = userOpt.get();

            // Check if user is active
            if (!user.getActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Account is disabled"));
            }

            AuthResponse authResponse = new AuthResponse(
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole(),
                    "Login successful"
            );

            return ResponseEntity.ok(new ApiResponse(true, "Login successful", authResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Login failed: " + e.getMessage()));
        }
    }

    // Logout (simple endpoint)
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful"));
    }

    // Helper method to encrypt card number (simple implementation)
    private String encryptCardNumber(String cardNumber) {
        // TTODO: Implement proper encryption
        // For now, store only last 4 digits
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "************" + cardNumber.substring(cardNumber.length() - 4);
    }
}
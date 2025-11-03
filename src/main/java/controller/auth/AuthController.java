package controller.auth;

import model.dto.auth.*;
import model.dto.ApiResponse;
import model.users.User;
import model.users.Address;
import model.users.PaymentMethod;
import model.enums.UserRole;
import service.admin.AdminUserService;
import service.users.UserService;
import service.users.AddressService;
import service.users.PaymentMethodService;
import service.security.PasswordService;
import service.security.CardValidationService;
import security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private CardValidationService cardValidationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminUserService adminUserService;

    // Register new CLIENT
    @PostMapping("/register/client")
    public ResponseEntity<ApiResponse> registerClient(@RequestBody RegisterClientRequest request) {
        try {
            // Check if user already exists
            if (userService.userExists(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(false, "Email already registered"));
            }

            // Encrypt password
            String encryptedPassword = passwordService.encryptPassword(request.getPassword());

            // Create new user
            User user = new User(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDocument(),
                    request.getPhone(),
                    request.getBirthDate(),
                    UserRole.CLIENT,
                    encryptedPassword // Encrypted password
            );

            User savedUser = userService.createUser(user);

            // Create address
            Address address = new Address(
                    savedUser.getEmail(),
                    request.getStreet(),
                    request.getNumber(),
                    request.getCity(),
                    request.getPostalCode(),
                    true
            );
            address.setApartment(request.getApartment());
            address.setAdditionalInfo(request.getAddressAdditionalInfo());
            addressService.createAddress(address);

            // Encrypt and create payment method
            String encryptedCardNumber = cardValidationService.encryptCardNumber(request.getCardNumber());

            PaymentMethod paymentMethod = new PaymentMethod(
                    savedUser.getEmail(),
                    request.getPaymentType(),
                    encryptedCardNumber,
                    request.getCardHolder(),
                    request.getExpirationDate(),
                    true
            );
            paymentMethodService.createPaymentMethod(paymentMethod);

            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().toString());

            AuthResponse authResponse = new AuthResponse(
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole(),
                    "Client registered successfully"
            );

            // Return response with token
            var responseData = new java.util.HashMap<String, Object>();
            responseData.put("user", authResponse);
            responseData.put("token", token);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Registration successful", responseData));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    // Register new ADMIN
    // Register new ADMIN (only existing admins can create new admins, or create first admin)
    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse> registerAdmin(
            @RequestParam(required = false) String adminEmail,
            @RequestBody RegisterAdminRequest request) {
        try {
            // Check if there are any existing admins
            long adminCount = adminUserService.getUserCountByRole(UserRole.ADMIN);

            // If no admins exist, allow creating the first one without validation
            if (adminCount == 0) {
                // First admin creation - no validation needed
                if (userService.userExists(request.getEmail())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ApiResponse(false, "Email already registered"));
                }
            } else {
                // There are existing admins - require validation
                if (adminEmail == null || adminEmail.trim().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "adminEmail parameter is required when admins already exist"));
                }

                // Verify that the requester is an admin
                Optional<User> adminOpt = userService.getUserByEmail(adminEmail);
                if (adminOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ApiResponse(false, "Admin user not found"));
                }

                User adminUser = adminOpt.get();
                if (adminUser.getRole() != UserRole.ADMIN || !adminUser.getActive()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new ApiResponse(false, "Only active administrators can register new admins"));
                }
            }

            if (userService.userExists(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(false, "Email already registered"));
            }

            // Encrypt password
            String encryptedPassword = passwordService.encryptPassword(request.getPassword());

            User user = new User(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDocument(),
                    request.getPhone(),
                    request.getBirthDate(),
                    UserRole.ADMIN,
                    encryptedPassword
            );

            User savedUser = userService.createUser(user);

            Address address = new Address(
                    savedUser.getEmail(),
                    request.getStreet(),
                    request.getNumber(),
                    request.getCity(),
                    request.getPostalCode(),
                    true
            );
            address.setApartment(request.getApartment());
            address.setAdditionalInfo(request.getAddressAdditionalInfo());
            addressService.createAddress(address);

            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().toString());

            AuthResponse authResponse = new AuthResponse(
                    savedUser.getEmail(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getRole(),
                    "Admin registered successfully"
            );

            var responseData = new java.util.HashMap<String, Object>();
            responseData.put("user", authResponse);
            responseData.put("token", token);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Registration successful", responseData));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            // Get user
            Optional<User> userOpt = userService.getUserByEmail(request.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Invalid email or password"));
            }

            User user = userOpt.get();

            // Verify password with BCrypt
            if (!passwordService.verifyPassword(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Invalid email or password"));
            }

            // Check if user is active
            if (!user.getActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "Account is disabled"));
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

            AuthResponse authResponse = new AuthResponse(
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole(),
                    "Login successful"
            );

            var responseData = new java.util.HashMap<String, Object>();
            responseData.put("user", authResponse);
            responseData.put("token", token);

            return ResponseEntity.ok(new ApiResponse(true, "Login successful", responseData));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Login failed: " + e.getMessage()));
        }
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful"));
    }

    // Validate token
    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Invalid token format"));
            }

            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            if (jwtUtil.validateToken(token, email)) {
                var data = new java.util.HashMap<String, Object>();
                data.put("email", email);
                data.put("role", role);

                return ResponseEntity.ok(new ApiResponse(true, "Token is valid", data));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Invalid or expired token"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Token validation failed: " + e.getMessage()));
        }
    }
}
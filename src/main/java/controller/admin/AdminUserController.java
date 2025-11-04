package controller.admin;

import model.dto.ApiResponse;
import model.dto.admin.UserResponse;
import model.dto.admin.UpdateUserStatusRequest;
import model.users.User;
import model.enums.UserRole;
import service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // Get all users
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = adminUserService.getAllUsers();
            List<UserResponse> responses = users.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Users retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get users: " + e.getMessage()));
        }
    }

    // Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse> getUsersByRole(@PathVariable UserRole role) {
        try {
            List<User> users = adminUserService.getUsersByRole(role);
            List<UserResponse> responses = users.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new ApiResponse(true, "Users with role " + role + " retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get users: " + e.getMessage()));
        }
    }

    // Get active users
    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveUsers() {
        try {
            List<User> users = adminUserService.getActiveUsers();
            List<UserResponse> responses = users.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Active users retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get users: " + e.getMessage()));
        }
    }

    // Get inactive users
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse> getInactiveUsers() {
        try {
            List<User> users = adminUserService.getInactiveUsers();
            List<UserResponse> responses = users.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Inactive users retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get users: " + e.getMessage()));
        }
    }

    // Get user by email
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {
        return adminUserService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(
                        new ApiResponse(true, "User found", mapToUserResponse(user))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "User not found")));
    }

    // Update user status
    @PutMapping("/{email}/status")
    public ResponseEntity<ApiResponse> updateUserStatus(
            @PathVariable String email,
            @RequestBody UpdateUserStatusRequest request) {
        try {
            User user = adminUserService.updateUserStatus(email, request.getActive());
            UserResponse response = mapToUserResponse(user);

            String message = request.getActive() ? "User activated" : "User deactivated";
            return ResponseEntity.ok(new ApiResponse(true, message, response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update user status: " + e.getMessage()));
        }
    }

    // Delete user
    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String email) {
        try {
            adminUserService.deleteUser(email);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete user: " + e.getMessage()));
        }
    }

    // Get user statistics
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getUserStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", adminUserService.getTotalUserCount());
            stats.put("activeUsers", adminUserService.getActiveUserCount());
            stats.put("clients", adminUserService.getUserCountByRole(UserRole.CLIENT));
            stats.put("admins", adminUserService.getUserCountByRole(UserRole.ADMIN));

            return ResponseEntity.ok(new ApiResponse(true, "Statistics retrieved", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get statistics: " + e.getMessage()));
        }
    }

    // Helper method to map User to UserResponse
    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDocument(),
                user.getBirthDate(),
                user.getPhone(),
                user.getRole(),
                user.getRegistrationDate(),
                user.getActive()
        );
    }
}
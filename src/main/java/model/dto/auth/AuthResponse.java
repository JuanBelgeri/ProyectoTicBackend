package model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.UserRole;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String message;
}
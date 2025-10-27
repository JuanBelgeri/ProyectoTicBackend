package model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.enums.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String document;
    private LocalDate birthDate;
    private String phone;
    private UserRole role;
    private LocalDateTime registrationDate;
    private Boolean active;
}
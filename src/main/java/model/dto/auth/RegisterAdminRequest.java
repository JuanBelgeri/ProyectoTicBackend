package model.dto.auth;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterAdminRequest {
    // User data
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String document;
    private LocalDate birthDate;
    private String phone;

    // Address data (required)
    private String street;
    private String number;
    private String apartment;
    private String city;
    private String postalCode;
    private String addressAdditionalInfo;
}
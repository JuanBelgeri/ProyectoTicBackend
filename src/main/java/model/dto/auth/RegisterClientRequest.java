package model.dto.auth;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterClientRequest {
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

    // Payment method data (required for clients)
    private String paymentType; // "CREDIT" or "DEBIT"
    private String cardNumber; // Will be encrypted
    private String cardHolder;
    private String expirationDate; // MM/YY
}
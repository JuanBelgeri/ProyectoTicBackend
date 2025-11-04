package model.dto.user;

import lombok.Data;

@Data
public class CreatePaymentMethodRequest {
    private String type; // "CREDIT" or "DEBIT"
    private String cardNumber; // Will be encrypted
    private String cardHolder;
    private String expirationDate; // MM/YY
    private Boolean isMain = false;
}
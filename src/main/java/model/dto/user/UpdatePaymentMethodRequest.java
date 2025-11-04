package model.dto.user;

import lombok.Data;

@Data
public class UpdatePaymentMethodRequest {
    private String type; // "CREDIT" or "DEBIT"
    private String cardNumber; // Will be encrypted if provided
    private String cardHolder;
    private String expirationDate; // MM/YY
    private Boolean isMain;
}
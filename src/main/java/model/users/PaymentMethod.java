package model.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to user

    @Column(nullable = false)
    private String type; // "CREDIT" or "DEBIT"

    @Column(nullable = false)
    private String cardNumberEncrypted; // Only last 4 digits visible

    @Column(nullable = false)
    private String cardHolder;

    private String expirationDate; // MM/YY

    @Column(nullable = false)
    private Boolean isMain = false;

    // Constructor
    public PaymentMethod(String userEmail, String type, String cardNumberEncrypted, String cardHolder, String expirationDate, Boolean isMain) {
        this.userEmail = userEmail;
        this.type = type;
        this.cardNumberEncrypted = cardNumberEncrypted;
        this.cardHolder = cardHolder;
        this.expirationDate = expirationDate;
        this.isMain = isMain;
    }
}
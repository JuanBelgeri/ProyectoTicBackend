package model.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to user

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    private String apartment;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    private String additionalInfo;

    @Column(nullable = false)
    private Boolean isMain = false;

    // Constructor
    public Address(String userEmail, String street, String number, String city, String postalCode, Boolean isMain) {
        this.userEmail = userEmail;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.isMain = isMain;
    }
}
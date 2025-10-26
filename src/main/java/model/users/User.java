package model.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.UserRole;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String email; // Email is the ID as per project requirements

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String document;
    private LocalDate birthDate;
    private String phone;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private Boolean active = true;

    // Constructor
    public User(String email, String firstName, String lastName, String document,
                String phone, LocalDate birthDate, UserRole role, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
        this.phone = phone;
        this.birthDate = birthDate;
        this.role = role;
        this.password = password;
        this.registrationDate = LocalDateTime.now();
    }
}
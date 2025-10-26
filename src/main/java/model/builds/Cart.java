package model.builds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userEmail; // One cart per user

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    // Constructor
    public Cart(String userEmail) {
        this.userEmail = userEmail;
        this.totalAmount = BigDecimal.ZERO;
        this.lastUpdated = LocalDateTime.now();
    }

    // Update last modified time
    public void updateLastModified() {
        this.lastUpdated = LocalDateTime.now();
    }
}
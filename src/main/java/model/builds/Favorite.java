package model.builds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to user

    @Column(nullable = false)
    private String itemType; // "PIZZA" or "HAMBURGER"

    @Column(nullable = false)
    private Long itemId; // ID of the pizza or hamburger

    @Column(nullable = false)
    private String customName; // Name given by user

    @Column(nullable = false)
    private LocalDateTime addedAt;

    // Constructor
    public Favorite(String userEmail, String itemType, Long itemId, String customName) {
        this.userEmail = userEmail;
        this.itemType = itemType;
        this.itemId = itemId;
        this.customName = customName;
        this.addedAt = LocalDateTime.now();
    }
}
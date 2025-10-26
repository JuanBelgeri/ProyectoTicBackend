package model.builds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.components.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "hamburgers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hamburger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to the user who created this hamburger

    @Column(nullable = false)
    private String name; // Custom name for the hamburger

    // Required components
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bread_id", nullable = false)
    private BreadType bread;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meat_id", nullable = false)
    private MeatType meat;

    // Optional components
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cheese_id")
    private CheeseType cheese;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hamburger_toppings",
            joinColumns = @JoinColumn(name = "hamburger_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    private List<Topping> toppings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hamburger_condiments",
            joinColumns = @JoinColumn(name = "hamburger_id"),
            inverseJoinColumns = @JoinColumn(name = "condiment_id")
    )
    private List<Condiment> condiments = new ArrayList<>();

    // Calculated fields
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor for creating a new hamburger
    public Hamburger(String userEmail, String name, BreadType bread, MeatType meat) {
        this.userEmail = userEmail;
        this.name = name;
        this.bread = bread;
        this.meat = meat;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        calculateTotalPrice();
    }

    // Method to add a topping
    public void addTopping(Topping topping) {
        if (!this.toppings.contains(topping)) {
            this.toppings.add(topping);
            this.updatedAt = LocalDateTime.now();
            calculateTotalPrice();
        }
    }

    // Method to remove a topping
    public void removeTopping(Topping topping) {
        if (this.toppings.remove(topping)) {
            this.updatedAt = LocalDateTime.now();
            calculateTotalPrice();
        }
    }

    // Method to add a condiment
    public void addCondiment(Condiment condiment) {
        if (!this.condiments.contains(condiment)) {
            this.condiments.add(condiment);
            this.updatedAt = LocalDateTime.now();
            calculateTotalPrice();
        }
    }

    // Method to remove a condiment
    public void removeCondiment(Condiment condiment) {
        if (this.condiments.remove(condiment)) {
            this.updatedAt = LocalDateTime.now();
            calculateTotalPrice();
        }
    }

    // Method to calculate total price
    public void calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        if (bread != null) total = total.add(bread.getPrice());
        if (meat != null) total = total.add(meat.getPrice());
        if (cheese != null) total = total.add(cheese.getPrice());

        if (toppings != null) {
            for (Topping topping : toppings) {
                total = total.add(topping.getPrice());
            }
        }

        if (condiments != null) {
            for (Condiment condiment : condiments) {
                total = total.add(condiment.getPrice());
            }
        }

        this.totalPrice = total;
    }

    // Validation method
    public boolean isValidHamburger() {
        return bread != null && meat != null;
    }
}
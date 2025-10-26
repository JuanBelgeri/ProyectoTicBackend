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
@Table(name = "pizzas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to the user who created this pizza

    @Column(nullable = false)
    private String name; // Custom name for the pizza

    // Required components
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id", nullable = false)
    private PizzaSize size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dough_id", nullable = false)
    private DoughType dough;

    // Optional components
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sauce_id")
    private SauceType sauce;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cheese_id")
    private CheeseType cheese;

    // Multiple toppings
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pizza_toppings",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    private List<Topping> toppings = new ArrayList<>();

    // Calculated fields
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor for creating a new pizza
    public Pizza(String userEmail, String name, PizzaSize size, DoughType dough) {
        this.userEmail = userEmail;
        this.name = name;
        this.size = size;
        this.dough = dough;
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

    // Method to calculate total price
    public void calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        if (size != null) total = total.add(size.getPrice());
        if (dough != null) total = total.add(dough.getPrice());
        if (sauce != null) total = total.add(sauce.getPrice());
        if (cheese != null) total = total.add(cheese.getPrice());

        if (toppings != null) {
            for (Topping topping : toppings) {
                total = total.add(topping.getPrice());
            }
        }

        this.totalPrice = total;
    }

    // Validation method
    public boolean isValidPizza() {
        if (size == null || dough == null) return false;
        return (sauce != null) || (cheese != null) || (toppings != null && !toppings.isEmpty());
    }
}
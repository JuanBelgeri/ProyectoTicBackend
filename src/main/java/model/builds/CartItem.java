package model.builds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cartId; // Reference to cart

    @Column(nullable = false)
    private String itemType; // "PIZZA" or "HAMBURGER"

    @Column(nullable = false)
    private Long itemId; // ID of the pizza or hamburger

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private Boolean priceUpdated;

    // Constructor
    public CartItem(Long cartId, String itemType, Long itemId, Integer quantity, BigDecimal unitPrice) {
        this.cartId = cartId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.priceUpdated = false;
        calculateSubtotal();
    }

    // Calculate subtotal
    public void calculateSubtotal() {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    // Update quantity
    public void updateQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
        calculateSubtotal();
    }
}
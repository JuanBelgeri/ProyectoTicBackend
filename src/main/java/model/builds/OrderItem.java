package model.builds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId; // Reference to order

    @Column(nullable = false)
    private String itemType; // "PIZZA" or "HAMBURGER"

    @Column(nullable = false)
    private Long itemId; // ID of the pizza or hamburger

    @Column(nullable = false)
    private String itemName; // Name at the time of order (for history)

    @Column(length = 1000)
    private String itemDescription; // Full description of the item

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Constructor
    public OrderItem(Long orderId, String itemType, Long itemId, String itemName,
                     String itemDescription, Integer quantity, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Calculate subtotal
    public void calculateSubtotal() {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
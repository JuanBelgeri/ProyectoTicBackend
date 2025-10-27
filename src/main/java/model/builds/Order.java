package model.builds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail; // Reference to user

    @Column(nullable = false)
    private Long addressId; // Reference to delivery address

    @Column(nullable = false)
    private Long paymentMethodId; // Reference to payment method

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @Column(length = 500)
    private String notes; // Notas adicionales del cliente

    // Constructor for creating new order
    public Order(String userEmail, Long addressId, Long paymentMethodId, BigDecimal totalAmount) {
        this.userEmail = userEmail;
        this.addressId = addressId;
        this.paymentMethodId = paymentMethodId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.EN_COLA;
        this.orderDate = LocalDateTime.now();
    }

    // Method to check if order can be cancelled
    public boolean canBeCancelled() {
        return this.status == OrderStatus.EN_COLA;
    }

    // Method to cancel order
    public void cancel() {
        if (!canBeCancelled()) {
            throw new IllegalStateException("Order cannot be cancelled in current status: " + this.status);
        }
        this.status = OrderStatus.CANCELADO;
    }

    // Method to update status
    public void updateStatus(OrderStatus newStatus) {
        // Validate status transitions
        if (this.status == OrderStatus.CANCELADO || this.status == OrderStatus.ENTREGADO) {
            throw new IllegalStateException("Cannot change status of completed order");
        }

        this.status = newStatus;

        if (newStatus == OrderStatus.ENTREGADO) {
            this.deliveryDate = LocalDateTime.now();
        }
    }
}
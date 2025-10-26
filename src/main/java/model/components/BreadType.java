package model.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "bread_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreadType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // CAMBIO: String en vez de Enum

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    // Constructor
    public BreadType(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.active = true;
    }
}
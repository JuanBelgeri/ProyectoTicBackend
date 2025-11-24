package model.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "meat_amounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeatAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    public MeatAmount(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.active = true;
    }
}

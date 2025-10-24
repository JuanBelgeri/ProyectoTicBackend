package entities.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "toppings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaTopping categoria;

    @Column(nullable = false)
    private Boolean vegetariano = false;

    @Column(nullable = false)
    private Boolean vegano = false;

    @Column(nullable = false)
    private Boolean activo = true;

    // Enum para categorías de toppings
    public enum CategoriaTopping {
        PROTEINA("Proteína"),
        VEGETAL("Vegetal"),
        QUESO("Queso"),
        ESPECIA("Especia"),
        FRUTA("Fruta");

        private final String descripcion;

        CategoriaTopping(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}

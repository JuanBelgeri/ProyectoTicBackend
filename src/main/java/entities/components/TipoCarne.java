package entities.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tipos_carne")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCarne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Carne nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean activo = true;

    public enum Carne {
        VACA("Carne de Vaca"),
        POLLO("Pollo"),
        CERDO("Cerdo"),
        SALMON("Salmón"),
        LENTEJAS("Lentejas"),
        SOJA("Soja");

        private final String descripcion;

        Carne(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}

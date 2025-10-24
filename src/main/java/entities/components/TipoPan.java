package entities.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tipos_pan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Pan nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean activo = true;

    public enum Pan {
        PAPA("Pan de Papa"),
        INTEGRAL("Pan Integral"),
        SIN_GLUTEN("Pan Sin Gluten"),
        BRIOCHE("Pan Brioche");

        private final String descripcion;

        Pan(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}

package entities.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tipos_queso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoQueso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Queso nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean activo = true;

    public enum Queso {
        MUZZARELLA("Muzzarella"),
        ROQUEFORT("Roquefort"),
        PARMESANO("Parmesano"),
        GORGONZOLA("Gorgonzola");

        private final String descripcion;

        Queso(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}

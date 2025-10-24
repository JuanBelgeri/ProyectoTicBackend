package entities.components;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "aderezos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aderezo {

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
    private TipoAderezo tipo;

    @Column(nullable = false)
    private Boolean picante = false;

    @Column(nullable = false)
    private Boolean vegetariano = true;

    @Column(nullable = false)
    private Boolean vegano = true;

    @Column(nullable = false)
    private Boolean activo = true;

    // Enum para tipos de aderezos
    public enum TipoAderezo {
        MAYONESA("Mayonesa"),
        KETCHUP("Ketchup"),
        MOSTAZA("Mostaza"),
        BARBACOA("Barbacoa"),
        RANCH("Ranch"),
        CEASAR("César"),
        CHIMICHURRI("Chimichurri"),
        AJI("Ají");

        private final String descripcion;

        TipoAderezo(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}

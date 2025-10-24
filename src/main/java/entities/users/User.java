package entities.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String email; //  email es el ID

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "ADMIN" o "CLIENT"

    // Datos comunes
    private String nombre;
    private String apellido;
    private String documento;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;

    // Datos específicos
    private String tarjetaCredito; // cliente

    // Constructor for controller usage
    public User(String email, String nombre, String apellido, String documento, 
                String telefono, String direccion, LocalDate fechaNacimiento, String role, String password, String tarjetaCredito) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.role = role;
        this.password = password;
        this.tarjetaCredito = tarjetaCredito;
    }
}


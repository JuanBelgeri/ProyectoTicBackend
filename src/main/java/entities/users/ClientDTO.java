package entities.users;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {
    private String nombre;
    private String apellido;
    private String documento;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private String tarjetaCredito;
    private String password;
}


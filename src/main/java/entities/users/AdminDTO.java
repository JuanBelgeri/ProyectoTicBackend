package entities.users;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminDTO {
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String email;
    private String direccion;
    private String password;
}


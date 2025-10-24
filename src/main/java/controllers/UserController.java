package controllers;

import entities.users.AdminDTO;
import entities.users.ClientDTO;
import entities.users.User;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class UserController {

    @Autowired
    private UserService userService;

    // Registrar un cliente
    @PostMapping("/register/client")
    public String registerClient(@RequestBody ClientDTO clientDTO) {
        try {
            userService.registerCliente(clientDTO);
            return "Cliente registrado correctamente";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Registrar un administrador
    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            userService.registerAdmin(adminDTO);
            return "Administrador registrado correctamente";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Buscar un usuario por email
    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}


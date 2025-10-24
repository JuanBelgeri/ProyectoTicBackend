package service;


import entities.users.AdminDTO;
import entities.users.User;
import entities.users.ClientDTO;

import repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Registrar cliente
    public User registerCliente(ClientDTO dto) throws IllegalArgumentException {
        // Validar que el email no exista
        if (userRepository.existsById(dto.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("CLIENT");

        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setDocumento(dto.getDocumento());
        user.setFechaNacimiento(dto.getFechaNacimiento());
        user.setTelefono(dto.getTelefono());
        user.setDireccion(dto.getDireccion());
        user.setTarjetaCredito(dto.getTarjetaCredito());

        return userRepository.save(user);
    }

    // Registrar administrador (funcionario)
    public User registerAdmin(AdminDTO dto) throws IllegalArgumentException {
        // Validar que el email no exista
        if (userRepository.existsById(dto.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ADMIN");

        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setDocumento(dto.getDocumento());
        user.setFechaNacimiento(dto.getFechaNacimiento());
        user.setTelefono(dto.getTelefono());
        user.setDireccion(dto.getDireccion());

        return userRepository.save(user);
    }

    // Autenticación
    public Optional<User> authenticate(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findById(email);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }
}


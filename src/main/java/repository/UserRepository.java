package repository;

import entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // métodos por defecto como findById(), save(), deleteById(), etc.
    boolean existsByEmail(String email);
    User findByEmail(String email);
}


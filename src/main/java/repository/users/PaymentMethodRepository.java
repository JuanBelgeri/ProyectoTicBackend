package repository.users;

import model.users.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUserEmail(String userEmail);
    Optional<PaymentMethod> findByUserEmailAndIsMainTrue(String userEmail);
}
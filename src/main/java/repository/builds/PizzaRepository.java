package repository.builds;

import model.builds.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByUserEmail(String userEmail);
    List<Pizza> findByUserEmailOrderByCreatedAtDesc(String userEmail);
}
package repository.components;

import model.components.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Long> {
    List<PizzaSize> findByActiveTrue();
}
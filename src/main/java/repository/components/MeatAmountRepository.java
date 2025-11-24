package repository.components;

import model.components.MeatAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeatAmountRepository extends JpaRepository<MeatAmount, Long> {
    List<MeatAmount> findByActiveTrue();
}

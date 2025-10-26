package repository.components;

import model.components.SauceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SauceTypeRepository extends JpaRepository<SauceType, Long> {
    List<SauceType> findByActiveTrue();
}
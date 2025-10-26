package repository.components;

import model.components.CheeseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CheeseTypeRepository extends JpaRepository<CheeseType, Long> {
    List<CheeseType> findByActiveTrue();
}
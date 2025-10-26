package repository.components;

import model.components.MeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MeatTypeRepository extends JpaRepository<MeatType, Long> {
    List<MeatType> findByActiveTrue();
}
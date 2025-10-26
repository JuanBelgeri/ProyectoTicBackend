package repository.components;

import model.components.BreadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BreadTypeRepository extends JpaRepository<BreadType, Long> {
    List<BreadType> findByActiveTrue();
}
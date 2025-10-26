package repository.components;

import model.components.DoughType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoughTypeRepository extends JpaRepository<DoughType, Long> {
    List<DoughType> findByActiveTrue();
}
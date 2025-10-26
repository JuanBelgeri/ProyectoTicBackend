package repository.components;

import model.components.Condiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CondimentRepository extends JpaRepository<Condiment, Long> {
    List<Condiment> findByActiveTrue();
}
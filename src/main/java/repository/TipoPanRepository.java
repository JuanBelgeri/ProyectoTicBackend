package repository;

import entities.components.TipoPan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoPanRepository extends JpaRepository<TipoPan, Long> {
    
    List<TipoPan> findByActivoTrue();
    
    TipoPan findByNombre(TipoPan.Pan nombre);
    
    boolean existsByNombre(TipoPan.Pan nombre);
}

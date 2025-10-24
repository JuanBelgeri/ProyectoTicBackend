package repository;

import entities.components.TipoQueso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoQuesoRepository extends JpaRepository<TipoQueso, Long> {
    
    List<TipoQueso> findByActivoTrue();
    
    TipoQueso findByNombre(TipoQueso.Queso nombre);
    
    boolean existsByNombre(TipoQueso.Queso nombre);
}

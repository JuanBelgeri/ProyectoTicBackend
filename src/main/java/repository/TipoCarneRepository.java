package repository;

import entities.components.TipoCarne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCarneRepository extends JpaRepository<TipoCarne, Long> {
    
    List<TipoCarne> findByActivoTrue();
    
    TipoCarne findByNombre(TipoCarne.Carne nombre);
    
    boolean existsByNombre(TipoCarne.Carne nombre);
}

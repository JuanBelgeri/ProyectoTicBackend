package repository;

import entities.components.TipoMasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoMasaRepository extends JpaRepository<TipoMasa, Long> {
    
    List<TipoMasa> findByActivoTrue();
    
    TipoMasa findByNombre(TipoMasa.Masa nombre);
    
    boolean existsByNombre(TipoMasa.Masa nombre);
}

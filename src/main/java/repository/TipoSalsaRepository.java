package repository;

import entities.components.TipoSalsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoSalsaRepository extends JpaRepository<TipoSalsa, Long> {
    
    List<TipoSalsa> findByActivoTrue();
    
    TipoSalsa findByNombre(TipoSalsa.Salsa nombre);
    
    boolean existsByNombre(TipoSalsa.Salsa nombre);
}

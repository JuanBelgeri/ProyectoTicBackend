package repository;

import entities.components.TamanioPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TamanioPizzaRepository extends JpaRepository<TamanioPizza, Long> {
    
    List<TamanioPizza> findByActivoTrue();
    
    TamanioPizza findByNombre(TamanioPizza.Tamanio nombre);
    
    boolean existsByNombre(TamanioPizza.Tamanio nombre);
}

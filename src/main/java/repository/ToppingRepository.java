package repository;

import entities.components.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
    
    List<Topping> findByActivoTrue();
    
    List<Topping> findByCategoriaAndActivoTrue(Topping.CategoriaTopping categoria);
    
    List<Topping> findByVegetarianoTrueAndActivoTrue();
    
    List<Topping> findByVeganoTrueAndActivoTrue();
    
    boolean existsByNombre(String nombre);
}

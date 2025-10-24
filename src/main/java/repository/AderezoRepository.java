package repository;

import entities.components.Aderezo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AderezoRepository extends JpaRepository<Aderezo, Long> {
    
    List<Aderezo> findByActivoTrue();
    
    List<Aderezo> findByTipoAndActivoTrue(Aderezo.TipoAderezo tipo);
    
    List<Aderezo> findByPicanteTrueAndActivoTrue();
    
    List<Aderezo> findByVegetarianoTrueAndActivoTrue();
    
    List<Aderezo> findByVeganoTrueAndActivoTrue();
    
    boolean existsByNombre(String nombre);
}

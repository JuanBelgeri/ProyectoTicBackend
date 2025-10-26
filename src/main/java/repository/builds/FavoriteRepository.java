package repository.builds;

import model.builds.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserEmail(String userEmail);
    List<Favorite> findByUserEmailAndItemType(String userEmail, String itemType);
    Optional<Favorite> findByUserEmailAndItemTypeAndItemId(String userEmail, String itemType, Long itemId);
    boolean existsByUserEmailAndItemTypeAndItemId(String userEmail, String itemType, Long itemId);
}
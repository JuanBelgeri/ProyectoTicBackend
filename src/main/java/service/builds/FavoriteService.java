package service.builds;

import model.builds.Favorite;
import repository.builds.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    // Add to favorites
    public Favorite addToFavorites(String userEmail, String itemType, Long itemId, String customName) {
        // Check if already exists
        if (favoriteRepository.existsByUserEmailAndItemTypeAndItemId(userEmail, itemType, itemId)) {
            throw new IllegalArgumentException("Item already in favorites");
        }

        Favorite favorite = new Favorite(userEmail, itemType, itemId, customName);
        return favoriteRepository.save(favorite);
    }

    // Get all favorites for user
    public List<Favorite> getFavoritesByUser(String userEmail) {
        return favoriteRepository.findByUserEmail(userEmail);
    }

    // Get favorites by type
    public List<Favorite> getFavoritesByType(String userEmail, String itemType) {
        return favoriteRepository.findByUserEmailAndItemType(userEmail, itemType);
    }

    // Remove from favorites
    public void removeFromFavorites(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    // Check if item is favorite
    public boolean isFavorite(String userEmail, String itemType, Long itemId) {
        return favoriteRepository.existsByUserEmailAndItemTypeAndItemId(userEmail, itemType, itemId);
    }
}
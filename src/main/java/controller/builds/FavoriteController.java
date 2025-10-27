package controller.builds;

import model.dto.ApiResponse;
import model.dto.favorite.*;
import model.builds.Favorite;
import service.builds.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // Add to favorites
    @PostMapping
    public ResponseEntity<ApiResponse> addToFavorites(
            @RequestParam String userEmail,
            @RequestBody AddToFavoritesRequest request) {
        try {
            Favorite favorite = favoriteService.addToFavorites(
                    userEmail,
                    request.getItemType(),
                    request.getItemId(),
                    request.getCustomName()
            );

            FavoriteResponse response = mapToFavoriteResponse(favorite);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Added to favorites", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to add to favorites: " + e.getMessage()));
        }
    }

    // Get all favorites for user
    @GetMapping
    public ResponseEntity<ApiResponse> getFavorites(@RequestParam String userEmail) {
        try {
            List<Favorite> favorites = favoriteService.getFavoritesByUser(userEmail);
            List<FavoriteResponse> responses = favorites.stream()
                    .map(this::mapToFavoriteResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Favorites retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get favorites: " + e.getMessage()));
        }
    }

    // Get favorites by type
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse> getFavoritesByType(
            @RequestParam String userEmail,
            @PathVariable String type) {
        try {
            List<Favorite> favorites = favoriteService.getFavoritesByType(userEmail, type.toUpperCase());
            List<FavoriteResponse> responses = favorites.stream()
                    .map(this::mapToFavoriteResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Favorites retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get favorites: " + e.getMessage()));
        }
    }

    // Remove from favorites
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<ApiResponse> removeFromFavorites(@PathVariable Long favoriteId) {
        try {
            favoriteService.removeFromFavorites(favoriteId);
            return ResponseEntity.ok(new ApiResponse(true, "Removed from favorites"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to remove from favorites: " + e.getMessage()));
        }
    }

    // Check if item is favorite
    @GetMapping("/check")
    public ResponseEntity<ApiResponse> isFavorite(
            @RequestParam String userEmail,
            @RequestParam String itemType,
            @RequestParam Long itemId) {
        try {
            boolean isFavorite = favoriteService.isFavorite(userEmail, itemType, itemId);
            return ResponseEntity.ok(new ApiResponse(true, "Checked", isFavorite));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to check favorite status: " + e.getMessage()));
        }
    }

    // Helper method
    private FavoriteResponse mapToFavoriteResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getItemType(),
                favorite.getItemId(),
                favorite.getCustomName(),
                favorite.getAddedAt()
        );
    }
}
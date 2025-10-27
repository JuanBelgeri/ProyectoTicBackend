package controller.builds;

import model.dto.ApiResponse;
import model.dto.cart.*;
import model.builds.*;
import service.builds.CartService;
import service.builds.PizzaService;
import service.builds.HamburgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private HamburgerService hamburgerService;

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(
            @RequestParam String userEmail,
            @RequestBody AddToCartRequest request) {
        try {
            CartItem cartItem;

            if ("PIZZA".equals(request.getItemType())) {
                cartItem = cartService.addPizzaToCart(userEmail, request.getItemId(), request.getQuantity());
            } else if ("HAMBURGER".equals(request.getItemType())) {
                cartItem = cartService.addHamburgerToCart(userEmail, request.getItemId(), request.getQuantity());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid item type"));
            }

            return ResponseEntity.ok(new ApiResponse(true, "Item added to cart", cartItem));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to add to cart: " + e.getMessage()));
        }
    }

    // Get cart
    @GetMapping
    public ResponseEntity<ApiResponse> getCart(@RequestParam String userEmail) {
        try {
            Cart cart = cartService.getCart(userEmail);
            List<CartItem> items = cartService.getCartItems(userEmail);

            List<CartItemResponse> itemResponses = items.stream()
                    .map(item -> {
                        String itemName = getItemName(item.getItemType(), item.getItemId());
                        return new CartItemResponse(
                                item.getId(),
                                item.getItemType(),
                                item.getItemId(),
                                itemName,
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getSubtotal()
                        );
                    })
                    .collect(Collectors.toList());

            CartResponse response = new CartResponse(
                    itemResponses,
                    cart.getTotalAmount(),
                    items.size()
            );

            return ResponseEntity.ok(new ApiResponse(true, "Cart retrieved", response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get cart: " + e.getMessage()));
        }
    }

    // Update cart item quantity
    @PutMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        try {
            if (quantity < 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Quantity must be at least 1"));
            }

            CartItem updatedItem = cartService.updateCartItemQuantity(itemId, quantity);
            return ResponseEntity.ok(new ApiResponse(true, "Quantity updated", updatedItem));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update quantity: " + e.getMessage()));
        }
    }

    // Remove item from cart
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable Long itemId) {
        try {
            cartService.removeCartItem(itemId);
            return ResponseEntity.ok(new ApiResponse(true, "Item removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to remove item: " + e.getMessage()));
        }
    }

    // Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(@RequestParam String userEmail) {
        try {
            cartService.clearCart(userEmail);
            return ResponseEntity.ok(new ApiResponse(true, "Cart cleared"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to clear cart: " + e.getMessage()));
        }
    }

    // Helper method to get item name
    private String getItemName(String itemType, Long itemId) {
        try {
            if ("PIZZA".equals(itemType)) {
                return pizzaService.getPizzaById(itemId)
                        .map(Pizza::getName)
                        .orElse("Unknown Pizza");
            } else if ("HAMBURGER".equals(itemType)) {
                return hamburgerService.getHamburgerById(itemId)
                        .map(Hamburger::getName)
                        .orElse("Unknown Hamburger");
            }
            return "Unknown Item";
        } catch (Exception e) {
            return "Unknown Item";
        }
    }
}
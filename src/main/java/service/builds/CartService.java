package service.builds;

import model.builds.Cart;
import model.builds.CartItem;
import model.builds.Pizza;
import model.builds.Hamburger;
import repository.builds.CartRepository;
import repository.builds.CartItemRepository;
import repository.builds.PizzaRepository;
import repository.builds.HamburgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private HamburgerRepository hamburgerRepository;

    // Get or create cart for user
    public Cart getOrCreateCart(String userEmail) {
        return cartRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    Cart cart = new Cart(userEmail);
                    return cartRepository.save(cart);
                });
    }

    // Add pizza to cart
    public CartItem addPizzaToCart(String userEmail, Long pizzaId, Integer quantity) {
        Cart cart = getOrCreateCart(userEmail);
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found"));

        CartItem cartItem = new CartItem(cart.getId(), "PIZZA", pizzaId, quantity, pizza.getTotalPrice());
        cartItem = cartItemRepository.save(cartItem);

        updateCartTotal(cart.getId());
        return cartItem;
    }

    // Add hamburger to cart
    public CartItem addHamburgerToCart(String userEmail, Long hamburgerId, Integer quantity) {
        Cart cart = getOrCreateCart(userEmail);
        Hamburger hamburger = hamburgerRepository.findById(hamburgerId)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));

        CartItem cartItem = new CartItem(cart.getId(), "HAMBURGER", hamburgerId, quantity, hamburger.getTotalPrice());
        cartItem = cartItemRepository.save(cartItem);

        updateCartTotal(cart.getId());
        return cartItem;
    }

    // Get cart items for user
    public List<CartItem> getCartItems(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        return cartItemRepository.findByCartId(cart.getId());
    }

    // Update cart item quantity
    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        cartItem.updateQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);

        updateCartTotal(cartItem.getCartId());
        return cartItem;
    }

    // Remove item from cart
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        Long cartId = cartItem.getCartId();
        cartItemRepository.deleteById(cartItemId);
        updateCartTotal(cartId);
    }

    // Clear cart
    public void clearCart(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        cartItemRepository.deleteByCartId(cart.getId());
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.updateLastModified();
        cartRepository.save(cart);
    }

    // Update cart total
    private void updateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        BigDecimal total = items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cart.updateLastModified();
        cartRepository.save(cart);
    }

    // Get cart with total
    public Cart getCart(String userEmail) {
        return getOrCreateCart(userEmail);
    }
}
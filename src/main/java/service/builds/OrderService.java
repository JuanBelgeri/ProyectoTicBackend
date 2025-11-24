package service.builds;

import model.builds.*;
import model.enums.OrderStatus;
import model.users.Address;
import model.users.PaymentMethod;
import repository.builds.*;
import repository.users.AddressRepository;
import repository.users.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private HamburgerService hamburgerService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    // Create order from cart
    public Order createOrderFromCart(String userEmail, Long addressId, Long paymentMethodId, String notes) {
        // Get cart
        Cart cart = cartService.getCart(userEmail);
        List<CartItem> cartItems = cartService.getCartItems(userEmail);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Validate address and payment method belong to user
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!address.getUserEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Address does not belong to user");
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found"));
        if (!paymentMethod.getUserEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Payment method does not belong to user");
        }

        // Create order
        Order order = new Order(userEmail, addressId, paymentMethodId, cart.getTotalAmount());
        if (notes != null && !notes.isEmpty()) {
            order.setNotes(notes);
        }
        order = orderRepository.save(order);

        // Create order items from cart items
        for (CartItem cartItem : cartItems) {
            String itemName = getItemName(cartItem.getItemType(), cartItem.getItemId());
            String itemDescription = getItemDescription(cartItem.getItemType(), cartItem.getItemId());

            OrderItem orderItem = new OrderItem(
                    order.getId(),
                    cartItem.getItemType(),
                    cartItem.getItemId(),
                    itemName,
                    itemDescription,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
            orderItemRepository.save(orderItem);
        }

        // Clear cart after order is created
        cartService.clearCart(userEmail);

        return order;
    }

    // Get order by id
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get orders for user
    public List<Order> getOrdersByUser(String userEmail) {
        return orderRepository.findByUserEmailOrderByOrderDateDesc(userEmail);
    }

    // Get all orders (for admin) - includes all orders including cancelled
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    // Get orders by status (for admin)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // Get orders by date (for admin/DGI)
    public List<Order> getOrdersByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return orderRepository.findByOrderDateBetween(start, end);
    }

    // Get order items
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Update order status (for admin)
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.updateStatus(newStatus);
        return orderRepository.save(order);
    }

    // Cancel order (for client)
    public Order cancelOrder(Long orderId, String userEmail) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Verify order belongs to user
        if (!order.getUserEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Order does not belong to user");
        }

        order.cancel();
        return orderRepository.save(order);
    }

    // Helper methods
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

    private String getItemDescription(String itemType, Long itemId) {
        try {
            if ("PIZZA".equals(itemType)) {
                Optional<Pizza> pizzaOpt = pizzaService.getPizzaById(itemId);
                if (pizzaOpt.isPresent()) {
                    Pizza pizza = pizzaOpt.get();
                    String toppings = pizza.getToppings().stream()
                            .map(t -> t.getName())
                            .collect(Collectors.joining(", "));
                    return String.format("Tamaño: %s, Masa: %s, Salsa: %s, Queso: %s, Toppings: %s",
                            pizza.getSize().getName(),
                            pizza.getDough().getName(),
                            pizza.getSauce() != null ? pizza.getSauce().getName() : "Sin salsa",
                            pizza.getCheese() != null ? pizza.getCheese().getName() : "Sin queso",
                            toppings.isEmpty() ? "Sin toppings" : toppings);
                }
            } else if ("HAMBURGER".equals(itemType)) {
                Optional<Hamburger> hamburgerOpt = hamburgerService.getHamburgerById(itemId);
                if (hamburgerOpt.isPresent()) {
                    Hamburger hamburger = hamburgerOpt.get();
                    String toppings = hamburger.getToppings().stream()
                            .map(t -> t.getName())
                            .collect(Collectors.joining(", "));
                    String condiments = hamburger.getCondiments().stream()
                            .map(c -> c.getName())
                            .collect(Collectors.joining(", "));
                    String cheeses = hamburger.getCheeses().stream()
                            .map(c -> c.getName())
                            .collect(Collectors.joining(", "));
                    return String.format("Pan: %s, Carne: %s, Queso: %s, Toppings: %s, Aderezos: %s",
                            hamburger.getBread().getName(),
                            hamburger.getMeat().getName(),
                            cheeses.isEmpty() ? "Sin queso" : cheeses,
                            toppings.isEmpty() ? "Sin toppings" : toppings,
                            condiments.isEmpty() ? "Sin aderezos" : condiments);
                }
            }
            return "Description not available";
        } catch (Exception e) {
            return "Description not available";
        }
    }
}
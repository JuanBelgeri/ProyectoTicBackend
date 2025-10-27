package controller.builds;

import model.dto.ApiResponse;
import model.dto.order.*;
import model.builds.Order;
import model.builds.OrderItem;
import model.enums.OrderStatus;
import service.builds.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create order from cart
    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(
            @RequestParam String userEmail,
            @RequestBody CreateOrderRequest request) {
        try {
            Order order = orderService.createOrderFromCart(
                    userEmail,
                    request.getAddressId(),
                    request.getPaymentMethodId(),
                    request.getNotes()
            );

            OrderResponse response = mapToOrderResponse(order);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Order created successfully", response));

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create order: " + e.getMessage()));
        }
    }

    // Get orders for user
    @GetMapping
    public ResponseEntity<ApiResponse> getUserOrders(@RequestParam String userEmail) {
        try {
            List<Order> orders = orderService.getOrdersByUser(userEmail);
            List<OrderResponse> responses = orders.stream()
                    .map(this::mapToOrderResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get orders: " + e.getMessage()));
        }
    }

    // Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(
                        new ApiResponse(true, "Order found", mapToOrderResponse(order))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Order not found")));
    }

    // Cancel order (client)
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(
            @PathVariable Long id,
            @RequestParam String userEmail) {
        try {
            Order order = orderService.cancelOrder(id, userEmail);
            OrderResponse response = mapToOrderResponse(order);

            return ResponseEntity.ok(new ApiResponse(true, "Order cancelled", response));

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to cancel order: " + e.getMessage()));
        }
    }

    // Get all orders (admin)
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            List<OrderResponse> responses = orders.stream()
                    .map(this::mapToOrderResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "All orders retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get orders: " + e.getMessage()));
        }
    }

    // Get orders by status (admin)
    @GetMapping("/admin/status/{status}")
    public ResponseEntity<ApiResponse> getOrdersByStatus(@PathVariable OrderStatus status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            List<OrderResponse> responses = orders.stream()
                    .map(this::mapToOrderResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new ApiResponse(true, "Orders with status " + status + " retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get orders: " + e.getMessage()));
        }
    }

    // Get orders by date (admin/DGI)
    @GetMapping("/admin/date/{date}")
    public ResponseEntity<ApiResponse> getOrdersByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Order> orders = orderService.getOrdersByDate(date);
            List<OrderResponse> responses = orders.stream()
                    .map(this::mapToOrderResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new ApiResponse(true, "Orders for date " + date + " retrieved", responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get orders: " + e.getMessage()));
        }
    }

    // Update order status (admin)
    @PutMapping("/admin/{id}/status")
    public ResponseEntity<ApiResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request) {
        try {
            Order order = orderService.updateOrderStatus(id, request.getStatus());
            OrderResponse response = mapToOrderResponse(order);

            return ResponseEntity.ok(
                    new ApiResponse(true, "Order status updated to " + request.getStatus(), response));

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update order status: " + e.getMessage()));
        }
    }

    // Helper method to map Order to OrderResponse
    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItem> items = orderService.getOrderItems(order.getId());

        List<OrderItemResponse> itemResponses = items.stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getItemType(),
                        item.getItemName(),
                        item.getItemDescription(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUserEmail(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getNotes(),
                itemResponses,
                items.size()
        );
    }
}
package service.integration;

import model.builds.Order;
import model.builds.OrderItem;
import model.enums.OrderStatus;
import model.enums.UserRole;
import model.users.User;
import repository.builds.OrderRepository;
import repository.builds.OrderItemRepository;
import repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class IntegrationService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    // DGI: Get all sales tickets for a specific date (excluding EN_COLA status)
    public Map<String, Object> getSalesTicketsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        // Get all orders for the date and filter out EN_COLA status
        List<Order> orders = orderRepository.findByOrderDateBetween(start, end)
                .stream()
                .filter(order -> order.getStatus() != OrderStatus.EN_COLA)
                .collect(Collectors.toList());

        // Calculate total revenue
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Map orders to tickets with user information
        List<Map<String, Object>> tickets = orders.stream()
                .map(order -> {
                    Map<String, Object> ticket = new HashMap<>();
                    ticket.put("ticketId", order.getId());
                    ticket.put("orderDate", order.getOrderDate());
                    ticket.put("status", order.getStatus());
                    ticket.put("totalAmount", order.getTotalAmount());
                    ticket.put("deliveryDate", order.getDeliveryDate());

                    // Get user information
                    Optional<User> userOpt = userRepository.findByEmail(order.getUserEmail());
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("email", user.getEmail());
                        userInfo.put("firstName", user.getFirstName());
                        userInfo.put("lastName", user.getLastName());
                        userInfo.put("document", user.getDocument());
                        userInfo.put("phone", user.getPhone());
                        ticket.put("user", userInfo);
                    } else {
                        // If user not found, just include email
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("email", order.getUserEmail());
                        ticket.put("user", userInfo);
                    }

                    // Get order items
                    List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
                    List<Map<String, Object>> itemDetails = items.stream()
                            .map(item -> {
                                Map<String, Object> itemMap = new HashMap<>();
                                itemMap.put("itemName", item.getItemName());
                                itemMap.put("itemType", item.getItemType());
                                itemMap.put("quantity", item.getQuantity());
                                itemMap.put("unitPrice", item.getUnitPrice());
                                itemMap.put("subtotal", item.getSubtotal());
                                return itemMap;
                            })
                            .collect(Collectors.toList());

                    ticket.put("items", itemDetails);

                    return ticket;
                })
                .collect(Collectors.toList());

        // Build response with total revenue
        Map<String, Object> response = new HashMap<>();
        response.put("orders", tickets);
        response.put("totalRevenue", totalRevenue);
        response.put("totalOrders", tickets.size());

        return response;
    }

    // BPS: Get active employees (admin users) with all information and total count
    public Map<String, Object> getEmployeeCount() {
        // Get all active admin employees with their full information
        List<Map<String, Object>> activeEmployees = userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .filter(user -> user.getActive())
                .map(user -> {
                    Map<String, Object> employeeInfo = new HashMap<>();
                    employeeInfo.put("email", user.getEmail());
                    employeeInfo.put("firstName", user.getFirstName());
                    employeeInfo.put("lastName", user.getLastName());
                    employeeInfo.put("document", user.getDocument());
                    employeeInfo.put("birthDate", user.getBirthDate());
                    employeeInfo.put("phone", user.getPhone());
                    employeeInfo.put("role", user.getRole());
                    employeeInfo.put("registrationDate", user.getRegistrationDate());
                    employeeInfo.put("active", user.getActive());
                    return employeeInfo;
                })
                .collect(Collectors.toList());

        // Get total count of active employees
        long totalActiveEmployees = activeEmployees.size();

        Map<String, Object> result = new HashMap<>();
        result.put("employees", activeEmployees);
        result.put("totalActiveEmployees", totalActiveEmployees);
        result.put("consultDate", LocalDateTime.now());

        return result;
    }

    // Additional: Get detailed employee information for BPS
    public List<Map<String, Object>> getEmployeeDetails() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .map(user -> {
                    Map<String, Object> employeeInfo = new HashMap<>();
                    employeeInfo.put("email", user.getEmail());
                    employeeInfo.put("firstName", user.getFirstName());
                    employeeInfo.put("lastName", user.getLastName());
                    employeeInfo.put("document", user.getDocument());
                    employeeInfo.put("registrationDate", user.getRegistrationDate());
                    employeeInfo.put("active", user.getActive());
                    return employeeInfo;
                })
                .collect(Collectors.toList());
    }
}
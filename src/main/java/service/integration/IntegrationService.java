package service.integration;

import model.builds.Order;
import model.builds.OrderItem;
import model.enums.UserRole;
import repository.builds.OrderRepository;
import repository.builds.OrderItemRepository;
import repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // DGI: Get all sales tickets for a specific date
    public List<Map<String, Object>> getSalesTicketsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);

        return orders.stream()
                .map(order -> {
                    Map<String, Object> ticket = new HashMap<>();
                    ticket.put("ticketId", order.getId());
                    ticket.put("userEmail", order.getUserEmail());
                    ticket.put("orderDate", order.getOrderDate());
                    ticket.put("status", order.getStatus());
                    ticket.put("totalAmount", order.getTotalAmount());

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
                    ticket.put("deliveryDate", order.getDeliveryDate());

                    return ticket;
                })
                .collect(Collectors.toList());
    }

    // BPS: Get count of employees (admin users) using the system
    public Map<String, Object> getEmployeeCount() {
        long adminCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .filter(user -> user.getActive())
                .count();

        long totalAdmins = userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .count();

        Map<String, Object> result = new HashMap<>();
        result.put("activeEmployees", adminCount);
        result.put("totalEmployees", totalAdmins);
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
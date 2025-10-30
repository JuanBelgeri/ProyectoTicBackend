package controller.integration;

import service.integration.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/integration")
@CrossOrigin(origins = "*")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    // ==================== DGI API ====================

    @GetMapping("/dgi/sales-tickets")
    public ResponseEntity<Map<String, Object>> getSalesTicketsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            var tickets = integrationService.getSalesTicketsByDate(date);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("consultDate", date);
            response.put("totalTickets", tickets.size());
            response.put("tickets", tickets);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al obtener tickets de venta: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    // ==================== BPS API ====================

    @GetMapping("/bps/employee-count")
    public ResponseEntity<Map<String, Object>> getEmployeeCount() {
        try {
            var employeeData = integrationService.getEmployeeCount();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", employeeData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al obtener cantidad de empleados: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }


    @GetMapping("/bps/employee-details")
    public ResponseEntity<Map<String, Object>> getEmployeeDetails() {
        try {
            var employees = integrationService.getEmployeeDetails();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("totalEmployees", employees.size());
            response.put("employees", employees);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al obtener detalles de empleados: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    // ==================== HEALTH CHECK ====================

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Integration API");
        response.put("timestamp", java.time.LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}
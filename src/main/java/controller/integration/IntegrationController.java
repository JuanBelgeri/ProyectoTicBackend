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

    // DGI API

    @GetMapping("/dgi/sales-tickets")
    public ResponseEntity<Map<String, Object>> getSalesTicketsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            var result = integrationService.getSalesTicketsByDate(date);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("consultDate", date);
            response.put("totalOrders", result.get("totalOrders"));
            response.put("totalRevenue", result.get("totalRevenue"));
            response.put("orders", result.get("orders"));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al obtener tickets de venta: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    // BPS API

    @GetMapping("/bps/employee-count")
    public ResponseEntity<Map<String, Object>> getEmployeeCount() {
        try {
            var employeeData = integrationService.getEmployeeCount();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("employees", employeeData.get("employees"));
            response.put("totalActiveEmployees", employeeData.get("totalActiveEmployees"));
            response.put("consultDate", employeeData.get("consultDate"));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error al obtener información de empleados: " + e.getMessage());

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

}
package controller.admin;

import model.dto.ApiResponse;
import model.dto.admin.*;
import service.admin.AdminComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/components")
@CrossOrigin(origins = "*")
public class AdminComponentController {

    @Autowired
    private AdminComponentService adminComponentService;

    // PIZZA SIZES

    @PostMapping("/pizza-sizes")
    public ResponseEntity<ApiResponse> createPizzaSize(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createPizzaSize(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Pizza size created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create pizza size: " + e.getMessage()));
        }
    }

    @PutMapping("/pizza-sizes/{id}")
    public ResponseEntity<ApiResponse> updatePizzaSize(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updatePizzaSize(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Pizza size updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update pizza size: " + e.getMessage()));
        }
    }

    @DeleteMapping("/pizza-sizes/{id}")
    public ResponseEntity<ApiResponse> deletePizzaSize(@PathVariable Long id) {
        try {
            adminComponentService.deletePizzaSize(id);
            return ResponseEntity.ok(new ApiResponse(true, "Pizza size deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete pizza size: " + e.getMessage()));
        }
    }

    // DOUGH TYPES

    @PostMapping("/dough-types")
    public ResponseEntity<ApiResponse> createDoughType(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createDoughType(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Dough type created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create dough type: " + e.getMessage()));
        }
    }

    @PutMapping("/dough-types/{id}")
    public ResponseEntity<ApiResponse> updateDoughType(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateDoughType(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Dough type updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update dough type: " + e.getMessage()));
        }
    }

    @DeleteMapping("/dough-types/{id}")
    public ResponseEntity<ApiResponse> deleteDoughType(@PathVariable Long id) {
        try {
            adminComponentService.deleteDoughType(id);
            return ResponseEntity.ok(new ApiResponse(true, "Dough type deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete dough type: " + e.getMessage()));
        }
    }

    // SAUCE TYPES

    @PostMapping("/sauce-types")
    public ResponseEntity<ApiResponse> createSauceType(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createSauceType(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Sauce type created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create sauce type: " + e.getMessage()));
        }
    }

    @PutMapping("/sauce-types/{id}")
    public ResponseEntity<ApiResponse> updateSauceType(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateSauceType(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Sauce type updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update sauce type: " + e.getMessage()));
        }
    }

    @DeleteMapping("/sauce-types/{id}")
    public ResponseEntity<ApiResponse> deleteSauceType(@PathVariable Long id) {
        try {
            adminComponentService.deleteSauceType(id);
            return ResponseEntity.ok(new ApiResponse(true, "Sauce type deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete sauce type: " + e.getMessage()));
        }
    }

    // CHEESE TYPES

    @PostMapping("/cheese-types")
    public ResponseEntity<ApiResponse> createCheeseType(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createCheeseType(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Cheese type created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create cheese type: " + e.getMessage()));
        }
    }

    @PutMapping("/cheese-types/{id}")
    public ResponseEntity<ApiResponse> updateCheeseType(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateCheeseType(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Cheese type updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update cheese type: " + e.getMessage()));
        }
    }

    @DeleteMapping("/cheese-types/{id}")
    public ResponseEntity<ApiResponse> deleteCheeseType(@PathVariable Long id) {
        try {
            adminComponentService.deleteCheeseType(id);
            return ResponseEntity.ok(new ApiResponse(true, "Cheese type deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete cheese type: " + e.getMessage()));
        }
    }

    // BREAD TYPES

    @PostMapping("/bread-types")
    public ResponseEntity<ApiResponse> createBreadType(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createBreadType(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Bread type created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create bread type: " + e.getMessage()));
        }
    }

    @PutMapping("/bread-types/{id}")
    public ResponseEntity<ApiResponse> updateBreadType(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateBreadType(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Bread type updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update bread type: " + e.getMessage()));
        }
    }

    @DeleteMapping("/bread-types/{id}")
    public ResponseEntity<ApiResponse> deleteBreadType(@PathVariable Long id) {
        try {
            adminComponentService.deleteBreadType(id);
            return ResponseEntity.ok(new ApiResponse(true, "Bread type deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete bread type: " + e.getMessage()));
        }
    }

    // MEAT TYPES

    @PostMapping("/meat-types")
    public ResponseEntity<ApiResponse> createMeatType(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createMeatType(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Meat type created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create meat type: " + e.getMessage()));
        }
    }

    @PutMapping("/meat-types/{id}")
    public ResponseEntity<ApiResponse> updateMeatType(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateMeatType(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Meat type updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update meat type: " + e.getMessage()));
        }
    }

    @DeleteMapping("/meat-types/{id}")
    public ResponseEntity<ApiResponse> deleteMeatType(@PathVariable Long id) {
        try {
            adminComponentService.deleteMeatType(id);
            return ResponseEntity.ok(new ApiResponse(true, "Meat type deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete meat type: " + e.getMessage()));
        }
    }

    // TOPPINGS

    @PostMapping("/toppings")
    public ResponseEntity<ApiResponse> createTopping(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createTopping(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Topping created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create topping: " + e.getMessage()));
        }
    }

    @PutMapping("/toppings/{id}")
    public ResponseEntity<ApiResponse> updateTopping(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateTopping(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Topping updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update topping: " + e.getMessage()));
        }
    }

    @DeleteMapping("/toppings/{id}")
    public ResponseEntity<ApiResponse> deleteTopping(@PathVariable Long id) {
        try {
            adminComponentService.deleteTopping(id);
            return ResponseEntity.ok(new ApiResponse(true, "Topping deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete topping: " + e.getMessage()));
        }
    }

    // CONDIMENTS

    @PostMapping("/condiments")
    public ResponseEntity<ApiResponse> createCondiment(@RequestBody CreateComponentRequest request) {
        try {
            var result = adminComponentService.createCondiment(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Condiment created", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create condiment: " + e.getMessage()));
        }
    }

    @PutMapping("/condiments/{id}")
    public ResponseEntity<ApiResponse> updateCondiment(
            @PathVariable Long id,
            @RequestBody UpdateComponentRequest request) {
        try {
            var result = adminComponentService.updateCondiment(
                    id, request.getName(), request.getPrice(), request.getActive());
            return ResponseEntity.ok(new ApiResponse(true, "Condiment updated", result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update condiment: " + e.getMessage()));
        }
    }

    @DeleteMapping("/condiments/{id}")
    public ResponseEntity<ApiResponse> deleteCondiment(@PathVariable Long id) {
        try {
            adminComponentService.deleteCondiment(id);
            return ResponseEntity.ok(new ApiResponse(true, "Condiment deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete condiment: " + e.getMessage()));
        }
    }
}
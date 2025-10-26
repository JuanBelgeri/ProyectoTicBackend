package service.builds;

import model.builds.Pizza;
import model.components.*;
import repository.builds.PizzaRepository;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    private DoughTypeRepository doughTypeRepository;

    @Autowired
    private SauceTypeRepository sauceTypeRepository;

    @Autowired
    private CheeseTypeRepository cheeseTypeRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    // Create pizza
    public Pizza createPizza(Pizza pizza) {
        pizza.setCreatedAt(LocalDateTime.now());
        pizza.setUpdatedAt(LocalDateTime.now());
        pizza.calculateTotalPrice();
        return pizzaRepository.save(pizza);
    }

    // Get pizza by id
    public Optional<Pizza> getPizzaById(Long id) {
        return pizzaRepository.findById(id);
    }

    // Get all pizzas for user
    public List<Pizza> getPizzasByUser(String userEmail) {
        return pizzaRepository.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }

    // Update pizza
    public Pizza updatePizza(Long id, Pizza updatedPizza) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found"));

        pizza.setName(updatedPizza.getName());
        pizza.setSize(updatedPizza.getSize());
        pizza.setDough(updatedPizza.getDough());
        pizza.setSauce(updatedPizza.getSauce());
        pizza.setCheese(updatedPizza.getCheese());
        pizza.setToppings(updatedPizza.getToppings());
        pizza.setUpdatedAt(LocalDateTime.now());
        pizza.calculateTotalPrice();

        return pizzaRepository.save(pizza);
    }

    // Add topping to pizza
    public Pizza addTopping(Long pizzaId, Long toppingId) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found"));
        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new IllegalArgumentException("Topping not found"));

        pizza.addTopping(topping);
        return pizzaRepository.save(pizza);
    }

    // Remove topping from pizza
    public Pizza removeTopping(Long pizzaId, Long toppingId) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Pizza not found"));
        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new IllegalArgumentException("Topping not found"));

        pizza.removeTopping(topping);
        return pizzaRepository.save(pizza);
    }

    // Delete pizza
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }

    // Get all pizzas (for admin)
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
}
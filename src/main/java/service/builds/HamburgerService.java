package service.builds;

import model.builds.Hamburger;
import model.components.*;
import repository.builds.HamburgerRepository;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HamburgerService {

    @Autowired
    private HamburgerRepository hamburgerRepository;

    @Autowired
    private BreadTypeRepository breadTypeRepository;

    @Autowired
    private MeatTypeRepository meatTypeRepository;

    @Autowired
    private CheeseTypeRepository cheeseTypeRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private CondimentRepository condimentRepository;

    // Create hamburger
    public Hamburger createHamburger(Hamburger hamburger) {
        hamburger.setCreatedAt(LocalDateTime.now());
        hamburger.setUpdatedAt(LocalDateTime.now());
        hamburger.calculateTotalPrice();
        return hamburgerRepository.save(hamburger);
    }

    // Get hamburger by id
    public Optional<Hamburger> getHamburgerById(Long id) {
        return hamburgerRepository.findById(id);
    }

    // Get all hamburgers for user
    public List<Hamburger> getHamburgersByUser(String userEmail) {
        return hamburgerRepository.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }

    // Update hamburger
    public Hamburger updateHamburger(Long id, Hamburger updatedHamburger) {
        Hamburger hamburger = hamburgerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));

        hamburger.setName(updatedHamburger.getName());
        hamburger.setBread(updatedHamburger.getBread());
        hamburger.setMeat(updatedHamburger.getMeat());
        hamburger.setCheeses(updatedHamburger.getCheeses());
        hamburger.setToppings(updatedHamburger.getToppings());
        hamburger.setCondiments(updatedHamburger.getCondiments());
        hamburger.setUpdatedAt(LocalDateTime.now());
        hamburger.calculateTotalPrice();

        return hamburgerRepository.save(hamburger);
    }

    // Add topping to hamburger
    public Hamburger addTopping(Long hamburgerId, Long toppingId) {
        Hamburger hamburger = hamburgerRepository.findById(hamburgerId)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));
        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new IllegalArgumentException("Topping not found"));

        hamburger.addTopping(topping);
        return hamburgerRepository.save(hamburger);
    }

    // Remove topping from hamburger
    public Hamburger removeTopping(Long hamburgerId, Long toppingId) {
        Hamburger hamburger = hamburgerRepository.findById(hamburgerId)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));
        Topping topping = toppingRepository.findById(toppingId)
                .orElseThrow(() -> new IllegalArgumentException("Topping not found"));

        hamburger.removeTopping(topping);
        return hamburgerRepository.save(hamburger);
    }

    // Add condiment to hamburger
    public Hamburger addCondiment(Long hamburgerId, Long condimentId) {
        Hamburger hamburger = hamburgerRepository.findById(hamburgerId)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));
        Condiment condiment = condimentRepository.findById(condimentId)
                .orElseThrow(() -> new IllegalArgumentException("Condiment not found"));

        hamburger.addCondiment(condiment);
        return hamburgerRepository.save(hamburger);
    }

    // Remove condiment from hamburger
    public Hamburger removeCondiment(Long hamburgerId, Long condimentId) {
        Hamburger hamburger = hamburgerRepository.findById(hamburgerId)
                .orElseThrow(() -> new IllegalArgumentException("Hamburger not found"));
        Condiment condiment = condimentRepository.findById(condimentId)
                .orElseThrow(() -> new IllegalArgumentException("Condiment not found"));

        hamburger.removeCondiment(condiment);
        return hamburgerRepository.save(hamburger);
    }

    // Delete hamburger
    public void deleteHamburger(Long id) {
        hamburgerRepository.deleteById(id);
    }

    // Get all hamburgers (for admin)
    public List<Hamburger> getAllHamburgers() {
        return hamburgerRepository.findAll();
    }
}
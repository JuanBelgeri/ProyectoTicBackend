package service.admin;
import model.components.*;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class AdminComponentService {

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;

    @Autowired
    private DoughTypeRepository doughTypeRepository;

    @Autowired
    private SauceTypeRepository sauceTypeRepository;

    @Autowired
    private CheeseTypeRepository cheeseTypeRepository;

    @Autowired
    private BreadTypeRepository breadTypeRepository;

    @Autowired
    private MeatTypeRepository meatTypeRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private CondimentRepository condimentRepository;

    // ==================== PIZZA SIZE ====================

    public PizzaSize createPizzaSize(String name, BigDecimal price) {
        PizzaSize pizzaSize = new PizzaSize(name, price);
        return pizzaSizeRepository.save(pizzaSize);
    }

    public PizzaSize updatePizzaSize(Long id, String name, BigDecimal price, Boolean active) {
        PizzaSize pizzaSize = pizzaSizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza size not found"));

        if (name != null) pizzaSize.setName(name);
        if (price != null) pizzaSize.setPrice(price);
        if (active != null) pizzaSize.setActive(active);

        return pizzaSizeRepository.save(pizzaSize);
    }

    public void deletePizzaSize(Long id) {
        pizzaSizeRepository.deleteById(id);
    }

    // ==================== DOUGH TYPE ====================

    public DoughType createDoughType(String name, BigDecimal price) {
        DoughType doughType = new DoughType(name, price);
        return doughTypeRepository.save(doughType);
    }

    public DoughType updateDoughType(Long id, String name, BigDecimal price, Boolean active) {
        DoughType doughType = doughTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dough type not found"));

        if (name != null) doughType.setName(name);
        if (price != null) doughType.setPrice(price);
        if (active != null) doughType.setActive(active);

        return doughTypeRepository.save(doughType);
    }

    public void deleteDoughType(Long id) {
        doughTypeRepository.deleteById(id);
    }

    // ==================== SAUCE TYPE ====================

    public SauceType createSauceType(String name, BigDecimal price) {
        SauceType sauceType = new SauceType(name, price);
        return sauceTypeRepository.save(sauceType);
    }

    public SauceType updateSauceType(Long id, String name, BigDecimal price, Boolean active) {
        SauceType sauceType = sauceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sauce type not found"));

        if (name != null) sauceType.setName(name);
        if (price != null) sauceType.setPrice(price);
        if (active != null) sauceType.setActive(active);

        return sauceTypeRepository.save(sauceType);
    }

    public void deleteSauceType(Long id) {
        sauceTypeRepository.deleteById(id);
    }

    // ==================== CHEESE TYPE ====================

    public CheeseType createCheeseType(String name, BigDecimal price) {
        CheeseType cheeseType = new CheeseType(name, price);
        return cheeseTypeRepository.save(cheeseType);
    }

    public CheeseType updateCheeseType(Long id, String name, BigDecimal price, Boolean active) {
        CheeseType cheeseType = cheeseTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cheese type not found"));

        if (name != null) cheeseType.setName(name);
        if (price != null) cheeseType.setPrice(price);
        if (active != null) cheeseType.setActive(active);

        return cheeseTypeRepository.save(cheeseType);
    }

    public void deleteCheeseType(Long id) {
        cheeseTypeRepository.deleteById(id);
    }

    // ==================== BREAD TYPE ====================

    public BreadType createBreadType(String name, BigDecimal price) {
        BreadType breadType = new BreadType(name, price);
        return breadTypeRepository.save(breadType);
    }

    public BreadType updateBreadType(Long id, String name, BigDecimal price, Boolean active) {
        BreadType breadType = breadTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bread type not found"));

        if (name != null) breadType.setName(name);
        if (price != null) breadType.setPrice(price);
        if (active != null) breadType.setActive(active);

        return breadTypeRepository.save(breadType);
    }

    public void deleteBreadType(Long id) {
        breadTypeRepository.deleteById(id);
    }

    // ==================== MEAT TYPE ====================

    public MeatType createMeatType(String name, BigDecimal price) {
        MeatType meatType = new MeatType(name, price);
        return meatTypeRepository.save(meatType);
    }

    public MeatType updateMeatType(Long id, String name, BigDecimal price, Boolean active) {
        MeatType meatType = meatTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meat type not found"));

        if (name != null) meatType.setName(name);
        if (price != null) meatType.setPrice(price);
        if (active != null) meatType.setActive(active);

        return meatTypeRepository.save(meatType);
    }

    public void deleteMeatType(Long id) {
        meatTypeRepository.deleteById(id);
    }

    // ==================== TOPPING ====================

    public Topping createTopping(String name, BigDecimal price) {
        Topping topping = new Topping(name, price);
        return toppingRepository.save(topping);
    }

    public Topping updateTopping(Long id, String name, BigDecimal price, Boolean active) {
        Topping topping = toppingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topping not found"));

        if (name != null) topping.setName(name);
        if (price != null) topping.setPrice(price);
        if (active != null) topping.setActive(active);

        return toppingRepository.save(topping);
    }

    public void deleteTopping(Long id) {
        toppingRepository.deleteById(id);
    }

    // ==================== CONDIMENT ====================

    public Condiment createCondiment(String name, BigDecimal price) {
        Condiment condiment = new Condiment(name, price);
        return condimentRepository.save(condiment);
    }

    public Condiment updateCondiment(Long id, String name, BigDecimal price, Boolean active) {
        Condiment condiment = condimentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Condiment not found"));

        if (name != null) condiment.setName(name);
        if (price != null) condiment.setPrice(price);
        if (active != null) condiment.setActive(active);

        return condimentRepository.save(condiment);
    }

    public void deleteCondiment(Long id) {
        condimentRepository.deleteById(id);
    }
}
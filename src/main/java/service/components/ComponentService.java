package service.components;

import model.components.*;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ComponentService {

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
    private MeatAmountRepository meatAmountRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private CondimentRepository condimentRepository;

    // Pizza Sizes
    public List<PizzaSize> getAllActivePizzaSizes() {
        return pizzaSizeRepository.findByActiveTrue();
    }

    public PizzaSize createPizzaSize(PizzaSize pizzaSize) {
        return pizzaSizeRepository.save(pizzaSize);
    }

    // Dough Types
    public List<DoughType> getAllActiveDoughTypes() {
        return doughTypeRepository.findByActiveTrue();
    }

    public DoughType createDoughType(DoughType doughType) {
        return doughTypeRepository.save(doughType);
    }

    // Sauce Types
    public List<SauceType> getAllActiveSauceTypes() {
        return sauceTypeRepository.findByActiveTrue();
    }

    public SauceType createSauceType(SauceType sauceType) {
        return sauceTypeRepository.save(sauceType);
    }

    // Cheese Types
    public List<CheeseType> getAllActiveCheeseTypes() {
        return cheeseTypeRepository.findByActiveTrue();
    }

    public CheeseType createCheeseType(CheeseType cheeseType) {
        return cheeseTypeRepository.save(cheeseType);
    }

    // Bread Types
    public List<BreadType> getAllActiveBreadTypes() {
        return breadTypeRepository.findByActiveTrue();
    }

    public BreadType createBreadType(BreadType breadType) {
        return breadTypeRepository.save(breadType);
    }

    // Meat Types
    public List<MeatType> getAllActiveMeatTypes() {
        return meatTypeRepository.findByActiveTrue();
    }

    // Meat Amounts
    public List<MeatAmount> getAllActiveMeatAmounts() {
        return meatAmountRepository.findByActiveTrue();
    }

    public MeatType createMeatType(MeatType meatType) {
        return meatTypeRepository.save(meatType);
    }

    // Toppings
    public List<Topping> getAllActiveToppings() {
        return toppingRepository.findByActiveTrue();
    }

    public Topping createTopping(Topping topping) {
        return toppingRepository.save(topping);
    }

    // Condiments
    public List<Condiment> getAllActiveCondiments() {
        return condimentRepository.findByActiveTrue();
    }

    public Condiment createCondiment(Condiment condiment) {
        return condimentRepository.save(condiment);
    }
}
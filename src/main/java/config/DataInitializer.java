package config;

import model.components.*;
import repository.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Initializing database with sample data...");

        // Pizza Sizes
        if (pizzaSizeRepository.count() == 0) {
            pizzaSizeRepository.save(new PizzaSize("Pequeña", new BigDecimal("8.00")));
            pizzaSizeRepository.save(new PizzaSize("Mediana", new BigDecimal("12.00")));
            pizzaSizeRepository.save(new PizzaSize("Grande", new BigDecimal("16.00")));
            System.out.println("✅ Pizza sizes created");
        }

        // Dough Types
        if (doughTypeRepository.count() == 0) {
            doughTypeRepository.save(new DoughType("Napolitana", new BigDecimal("2.00")));
            doughTypeRepository.save(new DoughType("Integral", new BigDecimal("2.50")));
            doughTypeRepository.save(new DoughType("Sin Gluten", new BigDecimal("3.00")));
            System.out.println("✅ Dough types created");
        }

        // Sauce Types
        if (sauceTypeRepository.count() == 0) {
            sauceTypeRepository.save(new SauceType("Tomate", new BigDecimal("1.50")));
            sauceTypeRepository.save(new SauceType("Pomodoro", new BigDecimal("2.00")));
            sauceTypeRepository.save(new SauceType("BBQ", new BigDecimal("2.50")));
            sauceTypeRepository.save(new SauceType("Blanca", new BigDecimal("2.00")));
            System.out.println("✅ Sauce types created");
        }

        // Cheese Types
        if (cheeseTypeRepository.count() == 0) {
            cheeseTypeRepository.save(new CheeseType("Mozzarella", new BigDecimal("2.00")));
            cheeseTypeRepository.save(new CheeseType("Roquefort", new BigDecimal("3.00")));
            cheeseTypeRepository.save(new CheeseType("Parmesano", new BigDecimal("2.50")));
            cheeseTypeRepository.save(new CheeseType("Cheddar", new BigDecimal("2.50")));
            System.out.println("✅ Cheese types created");
        }

        // Bread Types
        if (breadTypeRepository.count() == 0) {
            breadTypeRepository.save(new BreadType("Papa", new BigDecimal("2.00")));
            breadTypeRepository.save(new BreadType("Integral", new BigDecimal("2.50")));
            breadTypeRepository.save(new BreadType("Sin Gluten", new BigDecimal("3.00")));
            breadTypeRepository.save(new BreadType("Brioche", new BigDecimal("3.50")));
            System.out.println("✅ Bread types created");
        }

        // Meat Types
        if (meatTypeRepository.count() == 0) {
            meatTypeRepository.save(new MeatType("Carne de Vaca", new BigDecimal("5.00")));
            meatTypeRepository.save(new MeatType("Pollo", new BigDecimal("4.50")));
            meatTypeRepository.save(new MeatType("Cerdo", new BigDecimal("4.50")));
            meatTypeRepository.save(new MeatType("Salmón", new BigDecimal("6.00")));
            meatTypeRepository.save(new MeatType("Lentejas", new BigDecimal("3.50")));
            meatTypeRepository.save(new MeatType("Soja", new BigDecimal("3.50")));
            System.out.println("✅ Meat types created");
        }

        // Toppings
        if (toppingRepository.count() == 0) {
            toppingRepository.save(new Topping("Jamón", new BigDecimal("1.50")));
            toppingRepository.save(new Topping("Pepperoni", new BigDecimal("2.00")));
            toppingRepository.save(new Topping("Champiñones", new BigDecimal("1.50")));
            toppingRepository.save(new Topping("Pimientos", new BigDecimal("1.00")));
            toppingRepository.save(new Topping("Cebolla", new BigDecimal("0.75")));
            toppingRepository.save(new Topping("Aceitunas", new BigDecimal("1.25")));
            toppingRepository.save(new Topping("Tomate", new BigDecimal("1.00")));
            toppingRepository.save(new Topping("Piña", new BigDecimal("1.50")));
            toppingRepository.save(new Topping("Bacon", new BigDecimal("2.00")));
            toppingRepository.save(new Topping("Jalapeños", new BigDecimal("1.25")));
            System.out.println("✅ Toppings created");
        }

        // Condiments
        if (condimentRepository.count() == 0) {
            condimentRepository.save(new Condiment("Mayonesa", new BigDecimal("0.50")));
            condimentRepository.save(new Condiment("Ketchup", new BigDecimal("0.50")));
            condimentRepository.save(new Condiment("Mostaza", new BigDecimal("0.50")));
            condimentRepository.save(new Condiment("BBQ", new BigDecimal("0.75")));
            condimentRepository.save(new Condiment("Ranch", new BigDecimal("0.75")));
            condimentRepository.save(new Condiment("Picante", new BigDecimal("0.50")));
            System.out.println("✅ Condiments created");
        }

        System.out.println("✅ Database initialization completed!");
    }
}
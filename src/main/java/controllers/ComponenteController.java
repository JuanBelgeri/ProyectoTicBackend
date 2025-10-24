package controllers;

import entities.components.*;
import service.ComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/componentes")
@CrossOrigin(origins = "*")
public class ComponenteController {

    @Autowired
    private ComponenteService componenteService;

    // ========== TAMAÑOS PIZZA ==========
    @GetMapping("/pizza/tamanios")
    public List<TamanioPizza> getAllTamaniosPizza() {
        return componenteService.getAllTamaniosPizza();
    }
    
    @PostMapping("/pizza/tamanios")
    public TamanioPizza createTamanioPizza(@RequestParam TamanioPizza.Tamanio nombre, 
                                          @RequestParam BigDecimal precio) {
        return componenteService.createTamanioPizza(nombre, precio);
    }
    
    @PutMapping("/pizza/tamanios/{id}/precio")
    public TamanioPizza updatePrecioTamanioPizza(@PathVariable Long id, 
                                                @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTamanioPizza(id, precio);
    }

    // ========== TIPOS MASA ==========
    @GetMapping("/pizza/masas")
    public List<TipoMasa> getAllTiposMasa() {
        return componenteService.getAllTiposMasa();
    }
    
    @PostMapping("/pizza/masas")
    public TipoMasa createTipoMasa(@RequestParam TipoMasa.Masa nombre, 
                                  @RequestParam BigDecimal precio) {
        return componenteService.createTipoMasa(nombre, precio);
    }
    
    @PutMapping("/pizza/masas/{id}/precio")
    public TipoMasa updatePrecioTipoMasa(@PathVariable Long id, 
                                        @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTipoMasa(id, precio);
    }

    // ========== TIPOS SALSA ==========
    @GetMapping("/pizza/salsas")
    public List<TipoSalsa> getAllTiposSalsa() {
        return componenteService.getAllTiposSalsa();
    }
    
    @PostMapping("/pizza/salsas")
    public TipoSalsa createTipoSalsa(@RequestParam TipoSalsa.Salsa nombre, 
                                    @RequestParam BigDecimal precio) {
        return componenteService.createTipoSalsa(nombre, precio);
    }
    
    @PutMapping("/pizza/salsas/{id}/precio")
    public TipoSalsa updatePrecioTipoSalsa(@PathVariable Long id, 
                                          @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTipoSalsa(id, precio);
    }

    // ========== TIPOS QUESO ==========
    @GetMapping("/pizza/quesos")
    public List<TipoQueso> getAllTiposQueso() {
        return componenteService.getAllTiposQueso();
    }
    
    @PostMapping("/pizza/quesos")
    public TipoQueso createTipoQueso(@RequestParam TipoQueso.Queso nombre, 
                                    @RequestParam BigDecimal precio) {
        return componenteService.createTipoQueso(nombre, precio);
    }
    
    @PutMapping("/pizza/quesos/{id}/precio")
    public TipoQueso updatePrecioTipoQueso(@PathVariable Long id, 
                                          @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTipoQueso(id, precio);
    }

    // ========== TIPOS PAN ==========
    @GetMapping("/hamburguesa/panes")
    public List<TipoPan> getAllTiposPan() {
        return componenteService.getAllTiposPan();
    }
    
    @PostMapping("/hamburguesa/panes")
    public TipoPan createTipoPan(@RequestParam TipoPan.Pan nombre, 
                                @RequestParam BigDecimal precio) {
        return componenteService.createTipoPan(nombre, precio);
    }
    
    @PutMapping("/hamburguesa/panes/{id}/precio")
    public TipoPan updatePrecioTipoPan(@PathVariable Long id, 
                                      @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTipoPan(id, precio);
    }

    // ========== TIPOS CARNE ==========
    @GetMapping("/hamburguesa/carnes")
    public List<TipoCarne> getAllTiposCarne() {
        return componenteService.getAllTiposCarne();
    }
    
    @PostMapping("/hamburguesa/carnes")
    public TipoCarne createTipoCarne(@RequestParam TipoCarne.Carne nombre, 
                                    @RequestParam BigDecimal precio) {
        return componenteService.createTipoCarne(nombre, precio);
    }
    
    @PutMapping("/hamburguesa/carnes/{id}/precio")
    public TipoCarne updatePrecioTipoCarne(@PathVariable Long id, 
                                          @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTipoCarne(id, precio);
    }

    // ========== TOPPINGS ==========
    @GetMapping("/toppings")
    public List<Topping> getAllToppings() {
        return componenteService.getAllToppings();
    }
    
    @GetMapping("/toppings/categoria/{categoria}")
    public List<Topping> getToppingsByCategoria(@PathVariable Topping.CategoriaTopping categoria) {
        return componenteService.getToppingsByCategoria(categoria);
    }
    
    @GetMapping("/toppings/vegetarianos")
    public List<Topping> getToppingsVegetarianos() {
        return componenteService.getToppingsVegetarianos();
    }
    
    @GetMapping("/toppings/veganos")
    public List<Topping> getToppingsVeganos() {
        return componenteService.getToppingsVeganos();
    }
    
    @PostMapping("/toppings")
    public Topping createTopping(@RequestParam String nombre, 
                                @RequestParam String descripcion,
                                @RequestParam BigDecimal precio, 
                                @RequestParam Topping.CategoriaTopping categoria, 
                                @RequestParam Boolean vegetariano, 
                                @RequestParam Boolean vegano) {
        return componenteService.createTopping(nombre, descripcion, precio, categoria, vegetariano, vegano);
    }
    
    @PutMapping("/toppings/{id}/precio")
    public Topping updatePrecioTopping(@PathVariable Long id, 
                                      @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioTopping(id, precio);
    }

    // ========== ADEREZOS ==========
    @GetMapping("/aderezos")
    public List<Aderezo> getAllAderezos() {
        return componenteService.getAllAderezos();
    }
    
    @GetMapping("/aderezos/tipo/{tipo}")
    public List<Aderezo> getAderezosByTipo(@PathVariable Aderezo.TipoAderezo tipo) {
        return componenteService.getAderezosByTipo(tipo);
    }
    
    @GetMapping("/aderezos/picantes")
    public List<Aderezo> getAderezosPicantes() {
        return componenteService.getAderezosPicantes();
    }
    
    @PostMapping("/aderezos")
    public Aderezo createAderezo(@RequestParam String nombre, 
                                @RequestParam String descripcion,
                                @RequestParam BigDecimal precio, 
                                @RequestParam Aderezo.TipoAderezo tipo, 
                                @RequestParam Boolean picante) {
        return componenteService.createAderezo(nombre, descripcion, precio, tipo, picante);
    }
    
    @PutMapping("/aderezos/{id}/precio")
    public Aderezo updatePrecioAderezo(@PathVariable Long id, 
                                      @RequestParam BigDecimal precio) {
        return componenteService.updatePrecioAderezo(id, precio);
    }
}

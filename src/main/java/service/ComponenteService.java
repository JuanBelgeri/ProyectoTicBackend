package service;

import entities.components.*;
import repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ComponenteService {

    @Autowired
    private TamanioPizzaRepository tamanioPizzaRepository;
    
    @Autowired
    private TipoMasaRepository tipoMasaRepository;
    
    @Autowired
    private TipoSalsaRepository tipoSalsaRepository;
    
    @Autowired
    private TipoQuesoRepository tipoQuesoRepository;
    
    @Autowired
    private TipoPanRepository tipoPanRepository;
    
    @Autowired
    private TipoCarneRepository tipoCarneRepository;
    
    @Autowired
    private ToppingRepository toppingRepository;
    
    @Autowired
    private AderezoRepository aderezoRepository;

    // ========== TAMAÑOS PIZZA ==========
    public List<TamanioPizza> getAllTamaniosPizza() {
        return tamanioPizzaRepository.findByActivoTrue();
    }
    
    public TamanioPizza createTamanioPizza(TamanioPizza.Tamanio nombre, BigDecimal precio) {
        if (tamanioPizzaRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tamaño de pizza ya existe");
        }
        TamanioPizza tamanio = new TamanioPizza();
        tamanio.setNombre(nombre);
        tamanio.setPrecio(precio);
        return tamanioPizzaRepository.save(tamanio);
    }
    
    public TamanioPizza updatePrecioTamanioPizza(Long id, BigDecimal nuevoPrecio) {
        TamanioPizza tamanio = tamanioPizzaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tamaño de pizza no encontrado"));
        tamanio.setPrecio(nuevoPrecio);
        return tamanioPizzaRepository.save(tamanio);
    }

    // ========== TIPOS MASA ==========
    public List<TipoMasa> getAllTiposMasa() {
        return tipoMasaRepository.findByActivoTrue();
    }
    
    public TipoMasa createTipoMasa(TipoMasa.Masa nombre, BigDecimal precio) {
        if (tipoMasaRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tipo de masa ya existe");
        }
        TipoMasa masa = new TipoMasa();
        masa.setNombre(nombre);
        masa.setPrecio(precio);
        return tipoMasaRepository.save(masa);
    }
    
    public TipoMasa updatePrecioTipoMasa(Long id, BigDecimal nuevoPrecio) {
        TipoMasa masa = tipoMasaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de masa no encontrado"));
        masa.setPrecio(nuevoPrecio);
        return tipoMasaRepository.save(masa);
    }

    // ========== TIPOS SALSA ==========
    public List<TipoSalsa> getAllTiposSalsa() {
        return tipoSalsaRepository.findByActivoTrue();
    }
    
    public TipoSalsa createTipoSalsa(TipoSalsa.Salsa nombre, BigDecimal precio) {
        if (tipoSalsaRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tipo de salsa ya existe");
        }
        TipoSalsa salsa = new TipoSalsa();
        salsa.setNombre(nombre);
        salsa.setPrecio(precio);
        return tipoSalsaRepository.save(salsa);
    }
    
    public TipoSalsa updatePrecioTipoSalsa(Long id, BigDecimal nuevoPrecio) {
        TipoSalsa salsa = tipoSalsaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de salsa no encontrado"));
        salsa.setPrecio(nuevoPrecio);
        return tipoSalsaRepository.save(salsa);
    }

    // ========== TIPOS QUESO ==========
    public List<TipoQueso> getAllTiposQueso() {
        return tipoQuesoRepository.findByActivoTrue();
    }
    
    public TipoQueso createTipoQueso(TipoQueso.Queso nombre, BigDecimal precio) {
        if (tipoQuesoRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tipo de queso ya existe");
        }
        TipoQueso queso = new TipoQueso();
        queso.setNombre(nombre);
        queso.setPrecio(precio);
        return tipoQuesoRepository.save(queso);
    }
    
    public TipoQueso updatePrecioTipoQueso(Long id, BigDecimal nuevoPrecio) {
        TipoQueso queso = tipoQuesoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de queso no encontrado"));
        queso.setPrecio(nuevoPrecio);
        return tipoQuesoRepository.save(queso);
    }

    // ========== TIPOS PAN ==========
    public List<TipoPan> getAllTiposPan() {
        return tipoPanRepository.findByActivoTrue();
    }
    
    public TipoPan createTipoPan(TipoPan.Pan nombre, BigDecimal precio) {
        if (tipoPanRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tipo de pan ya existe");
        }
        TipoPan pan = new TipoPan();
        pan.setNombre(nombre);
        pan.setPrecio(precio);
        return tipoPanRepository.save(pan);
    }
    
    public TipoPan updatePrecioTipoPan(Long id, BigDecimal nuevoPrecio) {
        TipoPan pan = tipoPanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de pan no encontrado"));
        pan.setPrecio(nuevoPrecio);
        return tipoPanRepository.save(pan);
    }

    // ========== TIPOS CARNE ==========
    public List<TipoCarne> getAllTiposCarne() {
        return tipoCarneRepository.findByActivoTrue();
    }
    
    public TipoCarne createTipoCarne(TipoCarne.Carne nombre, BigDecimal precio) {
        if (tipoCarneRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El tipo de carne ya existe");
        }
        TipoCarne carne = new TipoCarne();
        carne.setNombre(nombre);
        carne.setPrecio(precio);
        return tipoCarneRepository.save(carne);
    }
    
    public TipoCarne updatePrecioTipoCarne(Long id, BigDecimal nuevoPrecio) {
        TipoCarne carne = tipoCarneRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tipo de carne no encontrado"));
        carne.setPrecio(nuevoPrecio);
        return tipoCarneRepository.save(carne);
    }

    // ========== TOPPINGS ==========
    public List<Topping> getAllToppings() {
        return toppingRepository.findByActivoTrue();
    }
    
    public List<Topping> getToppingsByCategoria(Topping.CategoriaTopping categoria) {
        return toppingRepository.findByCategoriaAndActivoTrue(categoria);
    }
    
    public List<Topping> getToppingsVegetarianos() {
        return toppingRepository.findByVegetarianoTrueAndActivoTrue();
    }
    
    public List<Topping> getToppingsVeganos() {
        return toppingRepository.findByVeganoTrueAndActivoTrue();
    }
    
    public Topping createTopping(String nombre, String descripcion, BigDecimal precio, 
                                Topping.CategoriaTopping categoria, Boolean vegetariano, Boolean vegano) {
        if (toppingRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El topping ya existe");
        }
        Topping topping = new Topping();
        topping.setNombre(nombre);
        topping.setDescripcion(descripcion);
        topping.setPrecio(precio);
        topping.setCategoria(categoria);
        topping.setVegetariano(vegetariano);
        topping.setVegano(vegano);
        return toppingRepository.save(topping);
    }
    
    public Topping updatePrecioTopping(Long id, BigDecimal nuevoPrecio) {
        Topping topping = toppingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Topping no encontrado"));
        topping.setPrecio(nuevoPrecio);
        return toppingRepository.save(topping);
    }

    // ========== ADEREZOS ==========
    public List<Aderezo> getAllAderezos() {
        return aderezoRepository.findByActivoTrue();
    }
    
    public List<Aderezo> getAderezosByTipo(Aderezo.TipoAderezo tipo) {
        return aderezoRepository.findByTipoAndActivoTrue(tipo);
    }
    
    public List<Aderezo> getAderezosPicantes() {
        return aderezoRepository.findByPicanteTrueAndActivoTrue();
    }
    
    public Aderezo createAderezo(String nombre, String descripcion, BigDecimal precio, 
                                Aderezo.TipoAderezo tipo, Boolean picante) {
        if (aderezoRepository.existsByNombre(nombre)) {
            throw new IllegalArgumentException("El aderezo ya existe");
        }
        Aderezo aderezo = new Aderezo();
        aderezo.setNombre(nombre);
        aderezo.setDescripcion(descripcion);
        aderezo.setPrecio(precio);
        aderezo.setTipo(tipo);
        aderezo.setPicante(picante);
        return aderezoRepository.save(aderezo);
    }
    
    public Aderezo updatePrecioAderezo(Long id, BigDecimal nuevoPrecio) {
        Aderezo aderezo = aderezoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Aderezo no encontrado"));
        aderezo.setPrecio(nuevoPrecio);
        return aderezoRepository.save(aderezo);
    }
}

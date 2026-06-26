package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private Long precioVenta;

    @Column(name = "precio_oferta")
    private Long precioOferta;

    @Column(name = "costo_produccion_total", precision = 19, scale = 4)
    private BigDecimal costoProduccionTotal;

    @Column(name = "imagen_url")
    private String imagenUrl;

    private boolean disponible;

    @Column(name = "requiere_cocina", nullable = false)
    private boolean requiereCocina = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaProducto categoria;

    // Relación con la tabla intermedia de ingredientes
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "producto_id") // Crea la FK en la tabla producto_insumos
    private List<ProductoInsumoEntity> ingredientes;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoVarianteEntity> variantes;

    //Creacion
    public ProductoEntity(String nombre, String descripcion, Long precioVenta, BigDecimal costoProduccionTotal,
                          String imagenUrl, boolean disponible, boolean requiereCocina, CategoriaProducto categoria,
                          List<ProductoInsumoEntity> ingredientes, List<ProductoVarianteEntity> variantes){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.costoProduccionTotal = costoProduccionTotal;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
        this.requiereCocina = requiereCocina;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.variantes = variantes != null ? variantes : new ArrayList<>(); // 🌟 Evitamos NullPointerException
    }

    //Obtencion
    public ProductoEntity(Long id, String nombre, String descripcion, Long precioVenta, BigDecimal costoProduccionTotal,
                          String imagenUrl, boolean disponible, boolean requiereCocina, CategoriaProducto categoria,
                          List<ProductoInsumoEntity> ingredientes, List<ProductoVarianteEntity> variantes){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.costoProduccionTotal = costoProduccionTotal;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
        this.requiereCocina = requiereCocina;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.variantes = variantes != null ? variantes : new ArrayList<>(); // 🌟 Evitamos NullPointerException
    }

    public ProductoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Long precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Long getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(Long precioOferta) {
        this.precioOferta = precioOferta;
    }

    public BigDecimal getCostoProduccionTotal() {
        return costoProduccionTotal;
    }

    public void setCostoProduccionTotal(BigDecimal costoProduccionTotal) {
        this.costoProduccionTotal = costoProduccionTotal;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isRequiereCocina() {
        return requiereCocina;
    }

    public void setRequiereCocina(boolean requiereCocina) {
        this.requiereCocina = requiereCocina;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    public List<ProductoInsumoEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoInsumoEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<ProductoVarianteEntity> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<ProductoVarianteEntity> variantes) {
        this.variantes = variantes;
    }
}

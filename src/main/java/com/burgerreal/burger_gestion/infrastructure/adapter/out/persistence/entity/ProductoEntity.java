package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
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

    @Column(name = "costo_produccion_total", precision = 19, scale = 4)
    private BigDecimal costoProduccionTotal;

    @Column(name = "imagen_url")
    private String imagenUrl;

    private boolean disponible;

    // Relación con la tabla intermedia de ingredientes
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "producto_id") // Crea la FK en la tabla producto_insumos
    private List<ProductoInsumoEntity> ingredientes;

    //Creacion
    public ProductoEntity(String nombre, String descripcion, Long precioVenta, BigDecimal costoProduccionTotal, String imagenUrl, boolean disponible, List<ProductoInsumoEntity> ingredientes){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.costoProduccionTotal = costoProduccionTotal;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
        this.ingredientes = ingredientes;
    }

    //Obtecion
    public ProductoEntity(Long id, String nombre, String descripcion, Long precioVenta, BigDecimal costoProduccionTotal, String imagenUrl, boolean disponible, List<ProductoInsumoEntity> ingredientes){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.costoProduccionTotal = costoProduccionTotal;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
        this.ingredientes = ingredientes;
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

    public List<ProductoInsumoEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoInsumoEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }
}

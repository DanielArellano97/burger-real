package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto_variantes")
@NoArgsConstructor
public class ProductoVarianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // Ej: "x5 unidades"

    @Column(name = "nombre_corto", nullable = false, length = 20)
    private String nombreCorto; // Ej: "x5"

    @Column(name = "precio_extra", nullable = false, precision = 19)
    private BigDecimal precioExtra; // Ej: 2500

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoEntity producto; // Relación con el producto padre

    //construccion
    public ProductoVarianteEntity(Long id, String nombre, String nombreCorto, BigDecimal precioExtra) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.precioExtra = precioExtra;
    }

    //obtener
    public ProductoVarianteEntity(Long id, String nombre, String nombreCorto, BigDecimal precioExtra, ProductoEntity producto) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.precioExtra = precioExtra;
        this.producto = producto;
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

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public BigDecimal getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(BigDecimal precioExtra) {
        this.precioExtra = precioExtra;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }
}

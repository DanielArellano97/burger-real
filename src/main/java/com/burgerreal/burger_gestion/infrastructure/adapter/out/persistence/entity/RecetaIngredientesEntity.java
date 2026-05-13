package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "receta_ingredientes")
public class RecetaIngredientesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", nullable = false)
    private RecetaEntity receta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_mercado_id", nullable = false)
    private ProductoMercadoEntity productoMercado;

    @Column(nullable = false)
    private double cantidad;

    //creacion
    public RecetaIngredientesEntity(RecetaEntity receta, ProductoMercadoEntity productoMercado, double cantidad){
        this.receta = receta;
        this.productoMercado = productoMercado;
        this.cantidad = cantidad;
    }

    //obtencion

    public RecetaIngredientesEntity(Long id, RecetaEntity receta, ProductoMercadoEntity productoMercado, double cantidad){
        this.id = id;
        this.receta = receta;
        this.productoMercado = productoMercado;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecetaEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetaEntity receta) {
        this.receta = receta;
    }

    public ProductoMercadoEntity getProductoMercado() {
        return productoMercado;
    }

    public void setProductoMercado(ProductoMercadoEntity productoMercado) {
        this.productoMercado = productoMercado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}

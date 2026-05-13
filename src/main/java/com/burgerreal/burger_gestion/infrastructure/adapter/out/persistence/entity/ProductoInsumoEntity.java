package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "producto_insumos")
public class ProductoInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id", nullable = false)
    private InsumoEntity insumo;

    @Column(nullable = false)
    private double cantidad;

    //Creacion
    public ProductoInsumoEntity (InsumoEntity insumo, Double cantidad){
        this.insumo = insumo;
        this.cantidad = cantidad;
    }

    //Obtecion
    public ProductoInsumoEntity (Long id, InsumoEntity insumo, Double cantidad){
        this.id = id;
        this.insumo = insumo;
        this.cantidad = cantidad;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsumoEntity getInsumo() {
        return insumo;
    }

    public void setInsumo(InsumoEntity insumo) {
        this.insumo = insumo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

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

    @Column(name = "permite_quitar", nullable = false)
    private boolean permiteQuitar;

    @Column(name = "permite_agregar", nullable = false)
    private boolean permiteAgregar;

    //Creacion
    public ProductoInsumoEntity(InsumoEntity insumo, Double cantidad, boolean permiteQuitar, boolean permiteAgregar) {
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.permiteQuitar = permiteQuitar;
        this.permiteAgregar = permiteAgregar;
    }

    //Obtecion
    public ProductoInsumoEntity(Long id, InsumoEntity insumo, Double cantidad, boolean permiteQuitar, boolean permiteAgregar) {
        this.id = id;
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.permiteQuitar = permiteQuitar;
        this.permiteAgregar = permiteAgregar;
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

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isPermiteQuitar() {
        return permiteQuitar;
    }

    public void setPermiteQuitar(boolean permiteQuitar) {
        this.permiteQuitar = permiteQuitar;
    }

    public boolean isPermiteAgregar() {
        return permiteAgregar;
    }

    public void setPermiteAgregar(boolean permiteAgregar) {
        this.permiteAgregar = permiteAgregar;
    }
}

package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "combos")
public class ComboEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "precio_combo", nullable = false)
    private Integer precioCombo;

    @Column(nullable = false)
    private Boolean disponible;

    // Relación One-To-Many hacia el detalle del combo
    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComboDetalleEntity> detalles;

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

    public Integer getPrecioCombo() {
        return precioCombo;
    }

    public void setPrecioCombo(Integer precioCombo) {
        this.precioCombo = precioCombo;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public List<ComboDetalleEntity> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ComboDetalleEntity> detalles) {
        this.detalles = detalles;
    }
}
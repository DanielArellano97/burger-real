package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mermas")
@NoArgsConstructor
public class MermaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insumo_id", nullable = false)
    private Long insumoId;

    @Column(name = "nombre_insumo", nullable = false)
    private String nombreInsumo;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(name = "costo_perdido", nullable = false, precision = 12, scale = 4)
    private BigDecimal costoPerdido;

    public MermaEntity(Long insumoId, String nombreInsumo, Double cantidad, String motivo, LocalDateTime fecha, BigDecimal costoPerdido) {
        this.insumoId = insumoId;
        this.nombreInsumo = nombreInsumo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fecha = fecha;
        this.costoPerdido = costoPerdido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCostoPerdido() {
        return costoPerdido;
    }

    public void setCostoPerdido(BigDecimal costoPerdido) {
        this.costoPerdido = costoPerdido;
    }
}

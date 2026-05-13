package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "compensaciones_venta")
public class CompensacionVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private VentaEntity venta;

    @Column(precision = 12, scale = 4)
    private BigDecimal costoInsumos;

    @Column(precision = 12, scale = 4)
    private BigDecimal comisionCocina;

    @Column(precision = 12, scale = 4)
    private BigDecimal comisionTransbank;

    @Column(precision = 12, scale = 4)
    private BigDecimal montoTotalRetenido;

    @Column(precision = 12, scale = 4)
    private BigDecimal montoReembolsado;

    private String observacion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    //constructor para insercion
    public CompensacionVentaEntity(BigDecimal costoInsumos, BigDecimal comisionCocina, BigDecimal comisionTransbank,
                                   BigDecimal montoTotalRetenido, BigDecimal montoReembolsado, String observacion,
                                   LocalDateTime fechaRegistro) {
        this.costoInsumos = costoInsumos;
        this.comisionCocina = comisionCocina;
        this.comisionTransbank = comisionTransbank;
        this.montoTotalRetenido = montoTotalRetenido;
        this.montoReembolsado = montoReembolsado;
        this.observacion = observacion;
        this.fechaRegistro = fechaRegistro;
    }

    //constructor para obtener
    public CompensacionVentaEntity(Long id, VentaEntity venta, BigDecimal costoInsumos, BigDecimal comisionCocina,
                                   BigDecimal comisionTransbank, BigDecimal montoTotalRetenido, BigDecimal montoReembolsado,
                                   String observacion, LocalDateTime fechaRegistro) {
        this.id = id;
        this.venta = venta;
        this.costoInsumos = costoInsumos;
        this.comisionCocina = comisionCocina;
        this.comisionTransbank = comisionTransbank;
        this.montoTotalRetenido = montoTotalRetenido;
        this.montoReembolsado = montoReembolsado;
        this.observacion = observacion;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }

    public BigDecimal getCostoInsumos() {
        return costoInsumos;
    }

    public void setCostoInsumos(BigDecimal costoInsumos) {
        this.costoInsumos = costoInsumos;
    }

    public BigDecimal getComisionCocina() {
        return comisionCocina;
    }

    public void setComisionCocina(BigDecimal comisionCocina) {
        this.comisionCocina = comisionCocina;
    }

    public BigDecimal getComisionTransbank() {
        return comisionTransbank;
    }

    public void setComisionTransbank(BigDecimal comisionTransbank) {
        this.comisionTransbank = comisionTransbank;
    }

    public BigDecimal getMontoTotalRetenido() {
        return montoTotalRetenido;
    }

    public void setMontoTotalRetenido(BigDecimal montoTotalRetenido) {
        this.montoTotalRetenido = montoTotalRetenido;
    }

    public BigDecimal getMontoReembolsado() {
        return montoReembolsado;
    }

    public void setMontoReembolsado(BigDecimal montoReembolsado) {
        this.montoReembolsado = montoReembolsado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}

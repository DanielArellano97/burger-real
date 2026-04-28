package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;

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

    @Column(name = "costo_insumos")
    private Long costoInsumos;

    @Column(name = "comision_cocina")
    private Long comisionCocina;

    @Column(name = "comision_transbank")
    private Long comisionTransbank;

    @Column(name = "monto_total_retenido")
    private Long montoTotalRetenido;

    @Column(name = "monto_reembolsado")
    private Long montoReembolsado;

    private String observacion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    //constructor para insercion
    public CompensacionVentaEntity(Long costoInsumos, Long comisionCocina, Long comisionTransbank,
                                   Long montoTotalRetenido, Long montoReembolsado, String observacion,
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
    public CompensacionVentaEntity(Long id, VentaEntity venta, Long costoInsumos, Long comisionCocina,
                                   Long comisionTransbank, Long montoTotalRetenido, Long montoReembolsado,
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

    public Long getCostoInsumos() {
        return costoInsumos;
    }

    public void setCostoInsumos(Long costoInsumos) {
        this.costoInsumos = costoInsumos;
    }

    public Long getComisionCocina() {
        return comisionCocina;
    }

    public void setComisionCocina(Long comisionCocina) {
        this.comisionCocina = comisionCocina;
    }

    public Long getComisionTransbank() {
        return comisionTransbank;
    }

    public void setComisionTransbank(Long comisionTransbank) {
        this.comisionTransbank = comisionTransbank;
    }

    public Long getMontoTotalRetenido() {
        return montoTotalRetenido;
    }

    public void setMontoTotalRetenido(Long montoTotalRetenido) {
        this.montoTotalRetenido = montoTotalRetenido;
    }

    public Long getMontoReembolsado() {
        return montoReembolsado;
    }

    public void setMontoReembolsado(Long montoReembolsado) {
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

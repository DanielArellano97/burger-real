package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Column(name = "monto_total_bruto", precision = 12, scale = 2)
    private BigDecimal montoTotalBruto;

    @Column(name = "costo_total_insumos", precision = 12, scale = 2)
    private BigDecimal costoTotalInsumos;

    @Column(name = "comision_pasarela", precision = 12, scale = 2)
    private BigDecimal comisionPasarela;

    @Column(name = "ganancia_neta", precision = 12, scale = 2)
    private BigDecimal gananciaNeta;

    // Aquí está la corrección: Ya no es un String, ahora es una relación profesional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPagoEntity metodoPago;

    //constructor para insertar en bdd
    public VentaEntity(BigDecimal montoTotalBruto, BigDecimal costoTotalInsumos,
                       BigDecimal comisionPasarela, BigDecimal gananciaNeta, MetodoPagoEntity metodoPago) {
        this.montoTotalBruto = montoTotalBruto;
        this.costoTotalInsumos = costoTotalInsumos;
        this.comisionPasarela = comisionPasarela;
        this.gananciaNeta = gananciaNeta;
        this.metodoPago = metodoPago;
    }

    //constructor para obtener desde bdd
    public VentaEntity(Long id, LocalDateTime fecha, BigDecimal montoTotalBruto, BigDecimal costoTotalInsumos,
                       BigDecimal comisionPasarela, BigDecimal gananciaNeta, MetodoPagoEntity metodoPago) {
        this.id = id;
        this.fecha = fecha;
        this.montoTotalBruto = montoTotalBruto;
        this.costoTotalInsumos = costoTotalInsumos;
        this.comisionPasarela = comisionPasarela;
        this.gananciaNeta = gananciaNeta;
        this.metodoPago = metodoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMontoTotalBruto() {
        return montoTotalBruto;
    }

    public void setMontoTotalBruto(BigDecimal montoTotalBruto) {
        this.montoTotalBruto = montoTotalBruto;
    }

    public BigDecimal getCostoTotalInsumos() {
        return costoTotalInsumos;
    }

    public void setCostoTotalInsumos(BigDecimal costoTotalInsumos) {
        this.costoTotalInsumos = costoTotalInsumos;
    }

    public BigDecimal getComisionPasarela() {
        return comisionPasarela;
    }

    public void setComisionPasarela(BigDecimal comisionPasarela) {
        this.comisionPasarela = comisionPasarela;
    }

    public BigDecimal getGananciaNeta() {
        return gananciaNeta;
    }

    public void setGananciaNeta(BigDecimal gananciaNeta) {
        this.gananciaNeta = gananciaNeta;
    }

    public MetodoPagoEntity getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEntity metodoPago) {
        this.metodoPago = metodoPago;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}

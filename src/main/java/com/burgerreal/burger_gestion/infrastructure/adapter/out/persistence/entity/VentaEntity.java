package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "ventas")
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fecha;

    @Column(name = "fecha_inicio_cocina", columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaInicioCocina;

    @Column(name = "fecha_entrega_cliente", columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaEntregaCliente;

    @Column(name = "monto_total_bruto", precision = 12)
    private BigDecimal montoTotalBruto;

    @Column(name = "costo_total_insumos", precision = 12)
    private BigDecimal costoTotalInsumos;

    @Column(name = "comision_pasarela", precision = 12, scale = 4)
    private BigDecimal comisionPasarela;

    @Column(name = "ganancia_neta", precision = 12)
    private BigDecimal gananciaNeta;

    @Column(name = "pago_confirmado", nullable = false)
    private boolean pagoConfirmado;

    @Column(name = "cargo_por_anulacion_cocina")
    private Long cargoPorAnulacionCocina;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVenta estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPagoEntity metodoPago;

    //constructor para insertar en bdd
    public VentaEntity(LocalDateTime fechaInicioCocina, LocalDateTime fechaEntregaCliente, BigDecimal montoTotalBruto, BigDecimal costoTotalInsumos,
                       BigDecimal comisionPasarela, BigDecimal gananciaNeta, boolean pagoConfirmado, EstadoVenta estado, MetodoPagoEntity metodoPago) {
        this.fechaInicioCocina = fechaInicioCocina;
        this.fechaEntregaCliente = fechaEntregaCliente;
        this.montoTotalBruto = montoTotalBruto;
        this.costoTotalInsumos = costoTotalInsumos;
        this.comisionPasarela = comisionPasarela;
        this.gananciaNeta = gananciaNeta;
        this.pagoConfirmado = pagoConfirmado;
        this.cargoPorAnulacionCocina = 0L;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    //constructor para obtener desde bdd
    public VentaEntity(Long id, LocalDateTime fecha, LocalDateTime fechaInicioCocina, LocalDateTime fechaEntregaCliente, BigDecimal montoTotalBruto, BigDecimal costoTotalInsumos,
                       BigDecimal comisionPasarela, BigDecimal gananciaNeta, boolean pagoConfirmado, Long cargoPorAnulacionCocina, EstadoVenta estado, MetodoPagoEntity metodoPago) {
        this.id = id;
        this.fecha = fecha;
        this.fechaInicioCocina = fechaInicioCocina;
        this.fechaEntregaCliente = fechaEntregaCliente;
        this.montoTotalBruto = montoTotalBruto;
        this.costoTotalInsumos = costoTotalInsumos;
        this.comisionPasarela = comisionPasarela;
        this.gananciaNeta = gananciaNeta;
        this.pagoConfirmado = pagoConfirmado;
        this.cargoPorAnulacionCocina = cargoPorAnulacionCocina;
        this.estado = estado;
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

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public boolean isPagoConfirmado() {
        return pagoConfirmado;
    }

    public void setPagoConfirmado(boolean pagoConfirmado) {
        this.pagoConfirmado = pagoConfirmado;
    }

    public LocalDateTime getFechaInicioCocina() {
        return fechaInicioCocina;
    }

    public void setFechaInicioCocina(LocalDateTime fechaInicioCocina) {
        this.fechaInicioCocina = fechaInicioCocina;
    }

    public LocalDateTime getFechaEntregaCliente() {
        return fechaEntregaCliente;
    }

    public void setFechaEntregaCliente(LocalDateTime fechaEntregaCliente) {
        this.fechaEntregaCliente = fechaEntregaCliente;
    }

    public Long getCargoPorAnulacionCocina() {
        return cargoPorAnulacionCocina;
    }

    public void setCargoPorAnulacionCocina(Long cargoPorAnulacion) {
        this.cargoPorAnulacionCocina = cargoPorAnulacion;
    }

    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}

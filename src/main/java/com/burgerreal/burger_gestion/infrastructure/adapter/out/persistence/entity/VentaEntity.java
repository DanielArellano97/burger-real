package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
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

    @Column(name = "monto_total_bruto", precision = 12, scale = 2)
    private Long montoTotalBruto;

    @Column(name = "costo_total_insumos", precision = 12, scale = 2)
    private Long costoTotalInsumos;

    @Column(name = "comision_pasarela", precision = 12, scale = 2)
    private Long comisionPasarela;

    @Column(name = "ganancia_neta", precision = 12, scale = 2)
    private Long gananciaNeta;

    // Aquí está la corrección: Ya no es un String, ahora es una relación profesional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_pago_id")
    private MetodoPagoEntity metodoPago;

    //constructor para insertar en bdd
    public VentaEntity(Long montoTotalBruto, Long costoTotalInsumos,
                       Long comisionPasarela, Long gananciaNeta, MetodoPagoEntity metodoPago) {
        this.montoTotalBruto = montoTotalBruto;
        this.costoTotalInsumos = costoTotalInsumos;
        this.comisionPasarela = comisionPasarela;
        this.gananciaNeta = gananciaNeta;
        this.metodoPago = metodoPago;
    }

    //constructor para obtener desde bdd
    public VentaEntity(Long id, LocalDateTime fecha, Long montoTotalBruto, Long costoTotalInsumos,
                       Long comisionPasarela, Long gananciaNeta, MetodoPagoEntity metodoPago) {
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

    public Long getMontoTotalBruto() {
        return montoTotalBruto;
    }

    public void setMontoTotalBruto(Long montoTotalBruto) {
        this.montoTotalBruto = montoTotalBruto;
    }

    public Long getCostoTotalInsumos() {
        return costoTotalInsumos;
    }

    public void setCostoTotalInsumos(Long costoTotalInsumos) {
        this.costoTotalInsumos = costoTotalInsumos;
    }

    public Long getComisionPasarela() {
        return comisionPasarela;
    }

    public void setComisionPasarela(Long comisionPasarela) {
        this.comisionPasarela = comisionPasarela;
    }

    public Long getGananciaNeta() {
        return gananciaNeta;
    }

    public void setGananciaNeta(Long gananciaNeta) {
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

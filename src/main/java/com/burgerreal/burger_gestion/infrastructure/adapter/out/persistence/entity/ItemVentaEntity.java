package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Table(name = "items_venta")
public class ItemVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private VentaEntity venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;

    @Column(nullable = false)
    private Integer cantidad;

    // Estos campos "congelan" el valor financiero en el momento de la venta
    @Column(name = "precio_venta_historico", nullable = false)
    private Long precioVentaHistorico;

    @Column(name = "costo_produccion_historico", precision = 19)
    private BigDecimal costoProduccionHistorico;

    // Constructor para insertar (usado por el Mapper)
    public ItemVentaEntity(VentaEntity venta, ProductoEntity producto, Integer cantidad,
                           Long precioVentaHistorico, BigDecimal costoProduccionHistorico) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVentaHistorico = precioVentaHistorico;
        this.costoProduccionHistorico = costoProduccionHistorico;
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

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecioVentaHistorico() {
        return precioVentaHistorico;
    }

    public void setPrecioVentaHistorico(Long precioVentaHistorico) {
        this.precioVentaHistorico = precioVentaHistorico;
    }

    public BigDecimal getCostoProduccionHistorico() {
        return costoProduccionHistorico;
    }

    public void setCostoProduccionHistorico(BigDecimal costoProduccionHistorico) {
        this.costoProduccionHistorico = costoProduccionHistorico;
    }
}

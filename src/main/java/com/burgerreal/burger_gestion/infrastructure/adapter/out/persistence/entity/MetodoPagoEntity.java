package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Table(name = "metodos_pago")
public class MetodoPagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "porcentaje_comision", precision = 5, scale = 2)
    private BigDecimal porcentajeComision;

    @Column(name = "comision_fija", precision = 10, scale = 2)
    private Long comisionFija;

    @Column(name = "esta_activo")
    private boolean estaActivo;

    //Constructor para insertar
    public MetodoPagoEntity(String nombre, BigDecimal porcentajeComision, Long comisionFija) {
        this.nombre = nombre;
        this.porcentajeComision = porcentajeComision;
        this.comisionFija = comisionFija;
        this.estaActivo = true;
    }

    //Constructor para consultar
    public MetodoPagoEntity(Long id, String nombre, BigDecimal porcentajeComision, Long comisionFija, boolean estaActivo) {
        this.id = id;
        this.nombre = nombre;
        this.porcentajeComision = porcentajeComision;
        this.comisionFija = comisionFija;
        this.estaActivo = estaActivo;
    }

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

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public Long getComisionFija() {
        return comisionFija;
    }

    public void setComisionFija(Long comisionFija) {
        this.comisionFija = comisionFija;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }
}

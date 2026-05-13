package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos_mercado")
@NoArgsConstructor
public class ProductoMercadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre; // Ej: "GAS_CILINDRO"

    @Column(name = "valor_compra", nullable = false)
    private Double valorCompra; // Ej: 25000

    @Column(name = "cantidad_total", nullable = false)
    private Double cantidadTotal; // Ej: 15

    @Column(name = "unidad_medida", nullable = false)
    private String unidadMedida; // Ej: "KG"

    //creacion
    public ProductoMercadoEntity(String nombre, Double valorCompra, Double cantidadTotal, String unidadMedida){
        this.nombre = nombre;
        this.valorCompra = valorCompra;
        this.cantidadTotal = cantidadTotal;
        this.unidadMedida = unidadMedida;
    }

    //obtencion
    public ProductoMercadoEntity(Long id, String nombre, Double valorCompra, Double cantidadTotal, String unidadMedida){
        this.id = id;
        this.nombre = nombre;
        this.valorCompra = valorCompra;
        this.cantidadTotal = cantidadTotal;
        this.unidadMedida = unidadMedida;
    }

    public ProductoMercadoEntity(Long id) {
        this.id = id;
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

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}

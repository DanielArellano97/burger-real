package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "insumos")
@NoArgsConstructor
public class InsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "costo_unitario", nullable = false, precision = 12, scale = 4)
    private BigDecimal costoUnitario;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaInsumo categoria;

    @Column(name = "es_inventariable")
    private boolean esInventariable;

    @OneToOne // Una receta genera UN insumo final (ej: Receta Palta -> Insumo Palta Procesada)
    @JoinColumn(name = "receta_id")
    private RecetaEntity receta;

    //Constructor para crear desde receta
    public InsumoEntity(String nombre, BigDecimal costoUnitario, int stockActual, int stockMinimo, String unidadMedida, CategoriaInsumo categoria, boolean esInventariable, RecetaEntity receta){
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.esInventariable = esInventariable;
        this.receta = receta;
    }

    //Constructor para buscar
    public InsumoEntity(Long id, String nombre, BigDecimal costoUnitario, Integer stockActual, Integer stockMinimo, String unidadMedida, CategoriaInsumo categoria, boolean esInventariable){
        this.id = id;
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.esInventariable = esInventariable;
    }

    //Constructor para actualizar stocks
    public InsumoEntity(Long id, String nombre, BigDecimal costoUnitario, int stockActual, int stockMinimo, String unidadMedida, CategoriaInsumo categoria, boolean esInventariable, RecetaEntity receta){
        this.id = id;
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.esInventariable = esInventariable;
        this.receta = receta;
    }

    // Constructor de conveniencia para mapeos rápidos
    public InsumoEntity(Long id) {
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

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public CategoriaInsumo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaInsumo categoria) {
        this.categoria = categoria;
    }

    public boolean isEsInventariable() {
        return esInventariable;
    }

    public void setEsInventariable(boolean esInventariable) {
        this.esInventariable = esInventariable;
    }

    public RecetaEntity getReceta() {
        return receta;
    }

    public void setReceta(RecetaEntity receta) {
        this.receta = receta;
    }
}

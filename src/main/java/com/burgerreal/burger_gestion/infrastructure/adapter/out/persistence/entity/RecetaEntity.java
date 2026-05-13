package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "recetas")
public class RecetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_insumo_final", nullable = false)
    private String nombreInsumoFinal;

    @Column(name = "categoria_insumo", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaInsumo categoriaInsumo;

    @Column(name = "unidad_medida", nullable = false)
    private String unidadMedida;

    @Column(nullable = false)
    private double rendimiento;

    @Column(name = "factor_gas", nullable = false)
    private double factorGas;

    @Column(name = "factor_aceite", nullable = false)
    private double factorAceite;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecetaIngredientesEntity> ingredientes;

    //creacion
    public RecetaEntity(String nombreInsumoFinal, CategoriaInsumo categoriaInsumo, double rendimiento, double factorGas, double factorAceite, List<RecetaIngredientesEntity> ingredientes){
        this.nombreInsumoFinal = nombreInsumoFinal;
        this.categoriaInsumo = categoriaInsumo;
        this.rendimiento = rendimiento;
        this.factorGas = factorGas;
        this.factorAceite = factorAceite;
        this.ingredientes = ingredientes;
    }

    //obtencion
    public RecetaEntity(Long id, String nombreInsumoFinal, CategoriaInsumo categoriaInsumo, double rendimiento, double factorGas, double factorAceite, List<RecetaIngredientesEntity> ingredientes){
        this.id = id;
        this.nombreInsumoFinal = nombreInsumoFinal;
        this.categoriaInsumo = categoriaInsumo;
        this.rendimiento = rendimiento;
        this.factorGas = factorGas;
        this.factorAceite = factorAceite;
        this.ingredientes = ingredientes;
    }

    // Agrega este constructor en RecetaEntity.java
    public RecetaEntity(String nombreInsumoFinal, CategoriaInsumo categoriaInsumo, String unidadMedida, double rendimiento, double factorGas, double factorAceite) {
        this.nombreInsumoFinal = nombreInsumoFinal;
        this.categoriaInsumo = categoriaInsumo;
        this.unidadMedida = unidadMedida;
        this.rendimiento = rendimiento;
        this.factorGas = factorGas;
        this.factorAceite = factorAceite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreInsumoFinal() {
        return nombreInsumoFinal;
    }

    public void setNombreInsumoFinal(String nombreInsumoFinal) {
        this.nombreInsumoFinal = nombreInsumoFinal;
    }

    public CategoriaInsumo getCategoriaInsumo() {
        return categoriaInsumo;
    }

    public void setCategoriaInsumo(CategoriaInsumo categoriaInsumo) {
        this.categoriaInsumo = categoriaInsumo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(double rendimiento) {
        this.rendimiento = rendimiento;
    }

    public double getFactorGas() {
        return factorGas;
    }

    public void setFactorGas(double factorGas) {
        this.factorGas = factorGas;
    }

    public double getFactorAceite() {
        return factorAceite;
    }

    public void setFactorAceite(double factorAceite) {
        this.factorAceite = factorAceite;
    }

    public List<RecetaIngredientesEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<RecetaIngredientesEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }
}

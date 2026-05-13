package com.burgerreal.burger_gestion.domain.model;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

import java.math.BigDecimal;

public record Insumo(
        Long id,
        String nombre,
        BigDecimal costoUnitario,
        Integer stockActual,
        Integer stockMinimo,
        String unidadMedida,
        CategoriaInsumo categoria,
        boolean esInventariable,
        Long recetaId
) {

    public static Insumo nuevoInsumo(String nombre, BigDecimal costoUnitario, Integer stockActual, Integer stockMinimo, String unidadMedida,
                                     CategoriaInsumo categoria, boolean esInventariable, Long recetaId){
        return new Insumo(
                null,
                nombre,
                costoUnitario,
                stockActual,
                stockMinimo,
                unidadMedida,
                categoria,
                esInventariable,
                recetaId
        );
    }

    public boolean requiereReposicion() {
        return stockActual <= stockMinimo;
    }

    public Insumo reducirStock(Double cantidad) {
        if (this.stockActual < Math.round(cantidad)) {
            throw new IllegalStateException("No hay suficiente stock de: " + nombre);
        }
        return new Insumo(
                id,
                nombre,
                costoUnitario,
                (int) (stockActual - cantidad),
                stockMinimo,
                unidadMedida,
                categoria,
                esInventariable,
                recetaId
        );
    }
}
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
        BigDecimal valorExtra,
        boolean esComercializadoraExtra,
        Long recetaId
) {

    public static Insumo nuevoInsumo(String nombre, BigDecimal costoUnitario, Integer stockActual, Integer stockMinimo,
                                     String unidadMedida, CategoriaInsumo categoria, boolean esInventariable,
                                     BigDecimal valorExtra, boolean esComercializadoraExtra, Long recetaId){
        return new Insumo(
                null,
                nombre,
                costoUnitario,
                stockActual,
                stockMinimo,
                unidadMedida,
                categoria,
                esInventariable,
                valorExtra,
                esComercializadoraExtra,
                recetaId
        );
    }

    public boolean requiereReposicion() {
        return stockActual <= stockMinimo;
    }

    public Insumo reducirStock(Double cantidad) {

        long cantidadAJustar = (long) Math.ceil(cantidad);

        if (this.stockActual < cantidadAJustar) {
            throw new IllegalStateException("No hay suficiente stock de: " + nombre);
        }
        return new Insumo(
                id,
                nombre,
                costoUnitario,
                (int) (stockActual - cantidadAJustar),
                stockMinimo,
                unidadMedida,
                categoria,
                esInventariable,
                valorExtra,
                esComercializadoraExtra,
                recetaId
        );
    }

    public Insumo aumentarStock(double cantidad) {

        long cantidadAJustar = (long) Math.ceil(cantidad);

        return new Insumo(
                id, nombre, costoUnitario, (int) (stockActual + cantidadAJustar),
                stockMinimo, unidadMedida, categoria, esInventariable, valorExtra, esComercializadoraExtra, recetaId
        );
    }
}
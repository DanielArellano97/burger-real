package com.burgerreal.burger_gestion.domain.model;

public record ProductoInsumo(
        Long id,
        Insumo insumo,
        Double cantidad
) {

    public static ProductoInsumo crearProductoInsumo(Insumo insumo, Double cantidad){
        return new ProductoInsumo(null, insumo, cantidad);
    }
}

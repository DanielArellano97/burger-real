package com.burgerreal.burger_gestion.domain.model;

public record RecetaIngredientes(
        Long id,
        ProductoMercado productoMercado,
        double cantidad
) {

    public static RecetaIngredientes crear(ProductoMercado productoMercado, double cantidad){
        return new RecetaIngredientes(
                null,
                productoMercado,
                cantidad
        );
    }
}

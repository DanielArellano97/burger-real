package com.burgerreal.burger_gestion.domain.model;

public record ComboDetalle(
        Long id,
        Producto producto,         // Tu modelo puro de Producto
        ProductoVariante varianteFija,     // Tu modelo puro de Variante (puede ser null si se elige en el momento)
        Integer cantidad
) {}

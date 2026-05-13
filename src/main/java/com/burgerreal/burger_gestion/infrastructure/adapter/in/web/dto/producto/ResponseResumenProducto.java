package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto;

public record ResponseResumenProducto(
        Long id,
        String nombre,
        String descripcion,
        Long precioVenta,
        Long costoProduccionTotal,
        String imagenUrl,
        boolean disponible
) {
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.item_venta;

public record ResponseItemVenta(
        Long productoId,
        String nombreProducto,
        Integer cantidad,
        Long precioVentaHistorico
) {
}

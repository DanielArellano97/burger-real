package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

import java.util.List;

public record CrearVentaRequest(
        List<ItemVentaRequest> items,
        boolean pagoConfirmado,
        Long metodoPagoId
) {
    public record ItemVentaRequest(
            Long productoId,
            Integer cantidad
    ) {}
}
package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

public record CrearVentaRequest(
        Long montoBruto,
        Long costoInsumos,
        boolean pagoConfirmado,
        Long metodoPagoId
) {
}
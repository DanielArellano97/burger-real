package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

public record EditarVentaRequest(
        Long montoBruto,
        Long costoInsumos,
        Long metodoPagoId
) {}

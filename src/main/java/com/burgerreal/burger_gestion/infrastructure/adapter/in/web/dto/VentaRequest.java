package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto;

public record VentaRequest(
        Long montoBruto,
        Long costoInsumos,
        Long metodoPagoId
) {
}
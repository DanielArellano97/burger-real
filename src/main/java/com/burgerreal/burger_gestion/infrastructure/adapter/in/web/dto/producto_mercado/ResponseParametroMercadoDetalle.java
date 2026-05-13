package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado;

public record ResponseParametroMercadoDetalle(
        Long id,
        String nombre,
        Double valorCompra,
        Double cantidadTotal,
        String unidadMedida
) {
}

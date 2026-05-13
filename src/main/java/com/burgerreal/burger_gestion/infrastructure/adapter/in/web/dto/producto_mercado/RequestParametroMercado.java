package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado;

public record RequestParametroMercado(
        String nombre,
        Double valorCompra,
        Double cantidadTotal,
        String unidadMedida
) {
}

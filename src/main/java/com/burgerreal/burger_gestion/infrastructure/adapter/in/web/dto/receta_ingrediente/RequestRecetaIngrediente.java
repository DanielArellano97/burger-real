package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta_ingrediente;

public record RequestRecetaIngrediente(
        Long productoMercadoId,
        double cantidad
) {
}

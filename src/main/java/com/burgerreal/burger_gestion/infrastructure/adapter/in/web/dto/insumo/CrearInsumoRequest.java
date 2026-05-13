package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

public record CrearInsumoRequest(
        String nombre,
        Integer stockActual,
        Integer stockMinimo,
        String unidadMedida,
        CategoriaInsumo categoria,
        boolean esInventariable,
        Long idReceta
) {
}

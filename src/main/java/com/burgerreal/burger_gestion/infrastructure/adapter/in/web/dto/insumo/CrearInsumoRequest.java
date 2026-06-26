package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

import java.math.BigDecimal;

public record CrearInsumoRequest(
        String nombre,
        Integer stockActual,
        Integer stockMinimo,
        String unidadMedida,
        CategoriaInsumo categoria,
        boolean esInventariable,
        BigDecimal valorExtra,
        boolean esComercializadoraExtra,
        Long idReceta
) {
}

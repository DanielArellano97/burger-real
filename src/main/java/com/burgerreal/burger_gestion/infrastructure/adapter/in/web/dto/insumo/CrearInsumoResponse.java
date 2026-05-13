package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

import java.math.BigDecimal;

public record CrearInsumoResponse(
        Long id,
        String nombre,
        BigDecimal costoUnitario,
        Integer stockActual,
        Integer stockMinimo,
        String unidadMedida,
        CategoriaInsumo categoria
) {
}

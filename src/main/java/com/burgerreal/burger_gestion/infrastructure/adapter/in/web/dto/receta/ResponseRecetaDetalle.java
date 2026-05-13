package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta;

import com.burgerreal.burger_gestion.domain.model.RecetaIngredientes;

import java.util.List;

public record ResponseRecetaDetalle(
        Long id,
        String nombreInsumoFinal,
        double rendimiento,
        double factorGas,
        double factorAceite,
        List<RecetaIngredientes> ingredientes
) {
}

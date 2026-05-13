package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta_ingrediente.RequestRecetaIngrediente;

import java.util.List;

public record RequestReceta(
        String nombreInsumoFinal,
        CategoriaInsumo categoriaInsumo,
        String unidadMedida,
        double rendimiento,
        double factorGas,
        double factorAceite,
        List<RequestRecetaIngrediente> ingredientes
) {
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

import java.math.BigDecimal;

public record ResponseResumenInsumo(
        Long id,
        String nombre,
        BigDecimal costoUnitario, // Importante para saber cuánto dinero hay en stock
        Integer stockActual,
        String unidadMedida, // "10 UNIDADES" o "5000 GR"
        CategoriaInsumo categoria,
        boolean bajoStock // <-- ¡Dato de oro!
) {

}

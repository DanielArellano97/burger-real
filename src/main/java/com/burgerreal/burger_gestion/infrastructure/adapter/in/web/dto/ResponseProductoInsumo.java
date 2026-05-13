package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto;

import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.ResponseResumenInsumo;

public record ResponseProductoInsumo(
        Long id,
        ResponseResumenInsumo insumo,
        Double cantidad
){
}

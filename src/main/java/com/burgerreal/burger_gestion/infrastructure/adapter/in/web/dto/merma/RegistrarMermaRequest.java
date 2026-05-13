package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.merma;

public record RegistrarMermaRequest(
        Long insumoId,
        Double cantidad,
        String motivo
) {
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.merma;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MermaListResponse(
        Long id,
        Long insumoId,
        String nombreInsumo, // Para histórico, por si el insumo se borra
        Double cantidad,
        String motivo,
        LocalDateTime fecha,
        BigDecimal costoPerdido
) {
}

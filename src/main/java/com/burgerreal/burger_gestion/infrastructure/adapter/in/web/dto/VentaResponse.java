package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record VentaResponse(
        Long id,
        LocalDateTime fecha,
        Long montoBruto
) {
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.combo;

import java.util.List;

public record ComboResponse(
        Long id,
        String nombre,
        String descripcion,
        Integer precioCombo,
        Boolean disponible,
        List<ComboDetalleResponse> detalles
) {
}

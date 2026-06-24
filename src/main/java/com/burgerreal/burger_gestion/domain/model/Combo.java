package com.burgerreal.burger_gestion.domain.model;

import java.util.List;

public record Combo(
    Long id,
    String nombre,
    String descripcion,
    Integer precioCombo,
    Boolean disponible,
    List<ComboDetalle> detalles
) {
}

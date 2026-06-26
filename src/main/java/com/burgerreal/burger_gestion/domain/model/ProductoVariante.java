package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;

public record ProductoVariante(
        Long id,
        String nombre,
        String nombreCorto,
        BigDecimal precioExtra
) {}
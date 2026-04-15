package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;

public record MetodoPago (

    Long id,
    String nombre,
    BigDecimal porcentajeComision,
    BigDecimal comisionFija,
    boolean estaActivo
){
}

package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Venta (
     Long id,
     LocalDateTime fecha,
     BigDecimal montoTotalBruto,
     BigDecimal costoTotalInsumos,
     BigDecimal comisionPasarela,
     BigDecimal gananciaNeta,
     MetodoPago metodoPago
){
}

package com.burgerreal.burger_gestion.domain.model;

import java.time.LocalDateTime;

public record Venta (
     Long id,
     LocalDateTime fecha,
     Long montoTotalBruto,
     Long costoTotalInsumos,
     Long comisionPasarela,
     Long gananciaNeta,
     MetodoPago metodoPago
){
}

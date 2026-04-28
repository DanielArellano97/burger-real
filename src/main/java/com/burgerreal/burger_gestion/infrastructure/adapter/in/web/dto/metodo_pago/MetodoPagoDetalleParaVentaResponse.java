package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago;

import java.math.BigDecimal;

public record MetodoPagoDetalleParaVentaResponse(
        Long id,
        String nombre,
        BigDecimal porcentajeComision,
        Long comisionFija
) {
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

import java.math.BigDecimal;

public record EditarVentaRequest(
        BigDecimal montoBruto,
        BigDecimal costoInsumos,
        Long metodoPagoId
) {}

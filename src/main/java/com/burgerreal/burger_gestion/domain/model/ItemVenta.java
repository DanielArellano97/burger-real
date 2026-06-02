package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;

public record ItemVenta(
        Long id,
        Producto producto,
        Integer cantidad,
        Long precioVentaHistorico,
        BigDecimal costoProduccionHistorico,
        boolean entregado
) {
}

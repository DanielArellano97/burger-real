package com.burgerreal.burger_gestion.application.port.in.venta;

import java.math.BigDecimal;

public interface EditarVentaUseCase {

    void ejecutar(Long id, BigDecimal montroBruto, BigDecimal costoInsumos, Long idMetodoPago);
}

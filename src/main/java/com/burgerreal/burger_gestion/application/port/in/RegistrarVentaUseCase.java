package com.burgerreal.burger_gestion.application.port.in;

import com.burgerreal.burger_gestion.domain.model.Venta;

public interface RegistrarVentaUseCase {

    Venta ejecutar(Long montoBruto, Long costos, Long metodoPagoId);
}

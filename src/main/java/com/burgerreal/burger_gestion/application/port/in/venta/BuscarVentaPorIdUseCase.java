package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.domain.model.Venta;

public interface BuscarVentaPorIdUseCase {

    Venta ejecutar(Long id);
}

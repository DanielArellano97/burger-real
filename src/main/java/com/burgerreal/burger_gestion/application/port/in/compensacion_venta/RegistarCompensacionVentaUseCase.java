package com.burgerreal.burger_gestion.application.port.in.compensacion_venta;

import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;

public interface RegistarCompensacionVentaUseCase {

    void ejecutar(CompensacionVenta compensacionVenta);
}

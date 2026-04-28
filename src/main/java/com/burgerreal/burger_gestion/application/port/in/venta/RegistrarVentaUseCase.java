package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.CrearVentaRequest;

public interface RegistrarVentaUseCase {

    Venta ejecutar(CrearVentaRequest crearVentaRequest);
}

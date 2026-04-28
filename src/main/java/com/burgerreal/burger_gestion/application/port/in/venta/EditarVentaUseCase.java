package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.EditarVentaRequest;

public interface EditarVentaUseCase {

    void ejecutar(Long id, EditarVentaRequest request);
}

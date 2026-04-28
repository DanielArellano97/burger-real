package com.burgerreal.burger_gestion.application.port.in.venta;

public interface AnularVentaPorIdUseCase {

    void ejecutar(Long ventaId, String motivo);
}

package com.burgerreal.burger_gestion.application.service.compensacion_venta;

import com.burgerreal.burger_gestion.application.port.in.compensacion_venta.RegistarCompensacionVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class RegistrarCompensacionVentaService implements RegistarCompensacionVentaUseCase {

    private final CompensacionVentaRepositoryPort compensacionVentaRepositoryPort;

    public RegistrarCompensacionVentaService(CompensacionVentaRepositoryPort compensacionVentaRepositoryPort) {
        this.compensacionVentaRepositoryPort = compensacionVentaRepositoryPort;
    }

    @Override
    @Transactional
    public void ejecutar(CompensacionVenta compensacionVenta) {
        compensacionVentaRepositoryPort.guardar(compensacionVenta);
    }
}

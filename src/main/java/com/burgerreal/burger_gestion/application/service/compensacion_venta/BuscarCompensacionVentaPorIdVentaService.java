package com.burgerreal.burger_gestion.application.service.compensacion_venta;

import com.burgerreal.burger_gestion.application.port.in.compensacion_venta.BuscarCompensacionVentaPorIdVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class BuscarCompensacionVentaPorIdVentaService implements BuscarCompensacionVentaPorIdVentaUseCase {

    private final CompensacionVentaRepositoryPort compensacionVentaRepositoryPort;

    public BuscarCompensacionVentaPorIdVentaService(CompensacionVentaRepositoryPort compensacionVentaRepositoryPort) {
        this.compensacionVentaRepositoryPort = compensacionVentaRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public CompensacionVenta ejecutar(Long id) {
        return compensacionVentaRepositoryPort.buscarCompensacionVentaPorVentaId(id).
                orElseThrow(() -> new EntityNotFoundException("No se encontro la compensacion de la venta con ID: "+id));
    }
}

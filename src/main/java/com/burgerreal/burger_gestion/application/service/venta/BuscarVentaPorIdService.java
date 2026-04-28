package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.BuscarVentaPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class BuscarVentaPorIdService implements BuscarVentaPorIdUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;

    public BuscarVentaPorIdService(VentaRepositoryPort ventaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Venta ejecutar(Long id) {
        return ventaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
    }
}

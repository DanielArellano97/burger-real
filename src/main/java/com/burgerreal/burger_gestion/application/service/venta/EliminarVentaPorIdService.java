package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.EliminarVentaPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class EliminarVentaPorIdService implements EliminarVentaPorIdUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;
    private final CompensacionVentaRepositoryPort compensacionVentaRepositoryPort;

    public EliminarVentaPorIdService(VentaRepositoryPort ventaRepositoryPort, CompensacionVentaRepositoryPort compensacionVentaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.compensacionVentaRepositoryPort = compensacionVentaRepositoryPort;
    }

    @Override
    @Transactional
    public void ejecutar(Long ventaId) {
        Venta venta = ventaRepositoryPort.buscarPorId(ventaId).orElseThrow(() -> new EntityNotFoundException("Venta con ID: "+ ventaId + " no fue encontrada"));

        compensacionVentaRepositoryPort.buscarCompensacionVentaPorVentaId(ventaId).ifPresent(c -> {
            throw new IllegalStateException(
                    "Acción denegada: La venta #" + ventaId +
                    " ya fue procesada con una compensación de $" + c.montoTotalRetenido() +
                    ". Para registros contables, esta venta no puede ser eliminada."
            );
        });

        ventaRepositoryPort.eliminarPorId(venta.id());
    }
}

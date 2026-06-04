package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.TerminarCocinaVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class TerminarCocinaVentaService implements TerminarCocinaVentaUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;

    public TerminarCocinaVentaService(VentaRepositoryPort ventaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
    }

    @Override
    @Transactional
    public Venta ejecutar(Long ventaId) {
        Venta venta = ventaRepositoryPort.buscarPorId(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + ventaId));

        // El dominio hace la magia logística
        Venta ventaTerminada = venta.terminarCocina();

        // Guardamos los cambios (gracias al CascadeType.ALL y el Mapper, guardará los ítems actualizados en BD)
        return ventaRepositoryPort.guardar(ventaTerminada);
    }
}

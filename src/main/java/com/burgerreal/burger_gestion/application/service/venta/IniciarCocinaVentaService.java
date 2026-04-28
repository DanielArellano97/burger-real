package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.IniciarCocinaVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class IniciarCocinaVentaService implements IniciarCocinaVentaUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;

    public IniciarCocinaVentaService(VentaRepositoryPort ventaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
    }

    @Override
    @Transactional
    public void ejecutar(Long ventaId) {
        Venta venta = ventaRepositoryPort.buscarPorId(ventaId).orElseThrow(() -> new EntityNotFoundException("No se encontro la venta con ID: "+ ventaId));
        Venta ventaEnCocina = venta.iniciarPreparacionVenta();

        ventaRepositoryPort.guardar(ventaEnCocina);

    }
}

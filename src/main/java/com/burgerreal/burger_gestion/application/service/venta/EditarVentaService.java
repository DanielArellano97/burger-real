package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.EditarVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.MetodoPagoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.EditarVentaRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class EditarVentaService implements EditarVentaUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;
    private final CalculoVentaService calculoVentaService;
    private final MetodoPagoRepositoryPort metodoPagoRepositoryPort;

    public EditarVentaService(VentaRepositoryPort ventaRepositoryPort, CalculoVentaService calculoVentaService, MetodoPagoRepositoryPort metodoPagoRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.calculoVentaService = calculoVentaService;
        this.metodoPagoRepositoryPort = metodoPagoRepositoryPort;
    }

    @Override
    @Transactional
    public void ejecutar(Long id, EditarVentaRequest request) {
        Venta ventaActual = ventaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta con ID: " + id + " no encontrada"));
        MetodoPago metodoPagoActualizado = metodoPagoRepositoryPort.buscarPorId(request.metodoPagoId())
                .orElseThrow(() -> new EntityNotFoundException("Metodo de pago con ID: "+ request.metodoPagoId() + " no encontrado"));

        // 1. Recalcular números basados en el nuevo monto bruto
        long nuevaComision = calculoVentaService.calcularComision(request.montoBruto(), metodoPagoActualizado);
        long nuevaGananciaNeta = request.montoBruto() - request.costoInsumos() - nuevaComision;

        // 2. Aplicar cambios en el dominio
        Venta ventaEditada = ventaActual.editarVenta(
                request.montoBruto(),
                request.costoInsumos(),
                nuevaComision,
                nuevaGananciaNeta,
                metodoPagoActualizado
        );

        ventaRepositoryPort.guardar(ventaEditada);
    }
}

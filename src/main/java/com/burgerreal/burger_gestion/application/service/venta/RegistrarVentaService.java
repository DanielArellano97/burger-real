package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.RegistrarVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.MetodoPagoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.CrearVentaRequest;
import org.springframework.transaction.annotation.Transactional;

public class RegistrarVentaService implements RegistrarVentaUseCase {

    private final MetodoPagoRepositoryPort metodoPagoRepositoryPort;
    private final VentaRepositoryPort ventaRepositoryPort;
    private final CalculoVentaService calculoVentaService;

    public RegistrarVentaService(MetodoPagoRepositoryPort metodoPagoRepositoryPort, VentaRepositoryPort ventaRepositoryPort, CalculoVentaService calculoVentaService) {
        this.metodoPagoRepositoryPort = metodoPagoRepositoryPort;
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.calculoVentaService = calculoVentaService;
    }

    @Override
    @Transactional
    public Venta ejecutar(CrearVentaRequest crearVentaRequest) {
        MetodoPago metodoPago = metodoPagoRepositoryPort.buscarPorId(crearVentaRequest.metodoPagoId()).orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con ID:" + crearVentaRequest.metodoPagoId()));

        long comision = calculoVentaService.calcularComision(crearVentaRequest.montoBruto(),metodoPago);
        long neto = calculoVentaService.calcularGananciaNeta(crearVentaRequest.montoBruto(), crearVentaRequest.costoInsumos(), comision);

        Venta nuevaVenta = Venta.nuevaVenta(comision,neto, crearVentaRequest, metodoPago);

        return ventaRepositoryPort.guardar(nuevaVenta);
    }
}

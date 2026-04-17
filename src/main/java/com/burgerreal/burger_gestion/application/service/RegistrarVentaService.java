package com.burgerreal.burger_gestion.application.service;

import com.burgerreal.burger_gestion.application.port.in.RegistrarVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.MetodoPagoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    @Transactional // Si algo falla al guardar, se hace rollback automático
    public Venta ejecutar(Long montoBruto, Long costos, Long metodoPagoId) {
        MetodoPago metodoPago = metodoPagoRepositoryPort.buscarPorId(metodoPagoId).orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con ID:" + metodoPagoId));

        long comision = calculoVentaService.calcularComision(montoBruto,metodoPago);
        long neto = calculoVentaService.calcularGananciaNeta(montoBruto, costos, comision);

        Venta nuevaVenta = new Venta(
                null,
                null,
                montoBruto,
                costos,
                comision,
                neto,
                metodoPago
        );

        return ventaRepositoryPort.guardar(nuevaVenta);
    }
}

package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.AnularVentaPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class AnularVentaPorIdService implements AnularVentaPorIdUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;
    private final CompensacionVentaRepositoryPort compensacionVentaRepositoryPort;
    private final CalculoVentaService calculoVentaService;

    public AnularVentaPorIdService(VentaRepositoryPort ventaRepositoryPort, CompensacionVentaRepositoryPort compensacionVentaRepositoryPort, CalculoVentaService calculoVentaService) {
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.compensacionVentaRepositoryPort = compensacionVentaRepositoryPort;
        this.calculoVentaService = calculoVentaService;
    }

    @Override
    @Transactional
    public void ejecutar(Long ventaId, String motivo) {

        // 1. Buscamos la venta original
        Venta venta = ventaRepositoryPort.buscarPorId(ventaId)
                .orElseThrow(() -> new EntityNotFoundException("Venta no existe"));

        //2. Calculamos las comisiones
        long comisionCocinero = venta.fechaInicioCocina() != null
                ? calculoVentaService.calcularMultaStaff(venta.montoTotalBruto())
                : 0;
        long comisionTransbank = venta.pagoConfirmado()
                ? calculoVentaService.calcularComision(venta.montoTotalBruto(), venta.metodoPago())
                : 0;

        //3. Ejecutamos logica de negocio (dominio)
        //   Creamos la compensacion de la venta y anulamos dicha venta.
        Venta ventaAnulada = venta.anular();
        CompensacionVenta compensacionVenta = CompensacionVenta.nuevaCompensacionVentaAnulada(ventaAnulada, motivo, comisionCocinero, comisionTransbank);

        //4. Persistir (aplicacion)
        //   Guardamos los cambios en bdd.
        ventaRepositoryPort.guardar(ventaAnulada);
        compensacionVentaRepositoryPort.guardar(compensacionVenta);
    }
}

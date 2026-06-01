package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.AnularVentaPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.*;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.RecetaRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class AnularVentaPorIdService implements AnularVentaPorIdUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;
    private final CompensacionVentaRepositoryPort compensacionVentaRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;
    private final RecetaRepositoryPort recetaRepositoryPort;
    private final CalculoVentaService calculoVentaService;

    public AnularVentaPorIdService(VentaRepositoryPort ventaRepositoryPort, CompensacionVentaRepositoryPort compensacionVentaRepositoryPort, InsumoRepositoryPort insumoRepositoryPort, RecetaRepositoryPort recetaRepositoryPort, CalculoVentaService calculoVentaService) {
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.compensacionVentaRepositoryPort = compensacionVentaRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
        this.recetaRepositoryPort = recetaRepositoryPort;
        this.calculoVentaService = calculoVentaService;
    }

    @Override
    @Transactional
    public void ejecutar(Long ventaId, String motivo) {

        // 1. Buscamos la venta original
        Venta venta = ventaRepositoryPort.buscarPorId(ventaId)
                .orElseThrow(() -> new EntityNotFoundException("Venta no existe"));

        // 2. Calculamos las comisiones/multas usando BigDecimal
        // Para el cocinero (ajusta calcularMultaStaff para que devuelva BigDecimal)
        BigDecimal comisionCocinero = venta.fechaInicioCocina() != null
                ? calculoVentaService.calcularMultaStaff(venta.montoTotalBruto())
                : BigDecimal.ZERO;

        // Para la pasarela de pago
        BigDecimal comisionTransbank = venta.pagoConfirmado()
                ? calculoVentaService.calcularComision(venta.montoTotalBruto(), venta.metodoPago())
                : BigDecimal.ZERO;

        if (!venta.burgerPreparada() && venta.items() != null) {
            for (ItemVenta item : venta.items()) {
                // Recorremos los ingredientes que tenía el producto al momento de la venta
                for (ProductoInsumo recetaItem : item.producto().ingredientes()) {
                    Insumo insumo = recetaItem.insumo();

                    if (insumo.esInventariable()) {
                        double rendimiento = recetaRepositoryPort.buscarPorId(insumo.recetaId())
                                .orElseThrow(() -> new RuntimeException("Receta no encontrada para el insumo: " + insumo.id())).rendimiento();

                        // Calculamos la cantidad exacta que se había descontado
                        BigDecimal cantidadADevolver = BigDecimal.valueOf(recetaItem.cantidad())
                                .multiply(BigDecimal.valueOf(item.cantidad()));

                        // Usamos un metodo del dominio del Insumo para aumentar el stock
                        // Nota: Si no tienes 'aumentarStock', puedes usar 'reducirStock' pasándole el valor en negativo: -cantidad/rendimiento
                        Insumo insumoActualizado = insumo.aumentarStock(cantidadADevolver.doubleValue() / rendimiento);
                        insumoRepositoryPort.guardar(insumoActualizado);
                    }
                }
            }
        }

        //3. Ejecutamos logica de negocio (dominio)
        //   Creamos la compensacion de la venta y anulamos dicha venta.
        Venta ventaAnulada = venta.anular(comisionCocinero);
        CompensacionVenta compensacionVenta = CompensacionVenta.nuevaCompensacionVentaAnulada(ventaAnulada, motivo, comisionCocinero, comisionTransbank);

        //4. Persistir (aplicacion)
        //   Guardamos los cambios en bdd.
        ventaRepositoryPort.guardar(ventaAnulada);
        compensacionVentaRepositoryPort.guardar(compensacionVenta);
    }
}

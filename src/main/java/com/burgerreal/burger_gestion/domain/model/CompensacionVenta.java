package com.burgerreal.burger_gestion.domain.model;

import java.time.LocalDateTime;

public record CompensacionVenta(
        Long id,
        Venta venta,
        Long costoInsumos,
        Long comisionCocina,
        Long comisionTransbank,
        Long montoTotalRetenido,
        Long montoReembolsado,
        String observacion,
        LocalDateTime fechaRegistro
) {

    public static CompensacionVenta nuevaCompensacionVentaAnulada(Venta venta, String motivo, long comisionCocinero, long comisionTransbank) {
        long multaInsumos = venta.calcularPerdidaInsumosSiEstaPreparada();
        long totalRetenido = multaInsumos + comisionCocinero + comisionTransbank;

        long montoReembolsar = 0;
        if (venta.pagoConfirmado()) {
            montoReembolsar = Math.max(0, venta.montoTotalBruto() - totalRetenido);
        }

        return new CompensacionVenta(null, venta, multaInsumos, comisionCocinero,
                comisionTransbank, totalRetenido, montoReembolsar,
                motivo, LocalDateTime.now());
    }
}

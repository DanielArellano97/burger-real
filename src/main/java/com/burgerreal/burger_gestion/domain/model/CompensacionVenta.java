package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompensacionVenta(
        Long id,
        Venta venta,
        BigDecimal costoInsumos,
        BigDecimal comisionCocina,
        BigDecimal comisionTransbank,
        BigDecimal montoTotalRetenido,
        BigDecimal montoReembolsado,
        String observacion,
        LocalDateTime fechaRegistro
) {

    public static CompensacionVenta nuevaCompensacionVentaAnulada(Venta venta, String motivo, BigDecimal comisionCocinero, BigDecimal comisionTransbank) {
        BigDecimal multaInsumos = venta.calcularPerdidaInsumosSiEstaPreparada();
        BigDecimal totalRetenido = multaInsumos.add(comisionCocinero).add(comisionTransbank);

        BigDecimal montoReembolsar = BigDecimal.ZERO;
        if (venta.pagoConfirmado()) {
            // Bruto - Retenciones. Usamos .max(ZERO) para no devolver saldos negativos
            montoReembolsar = venta.montoTotalBruto().subtract(totalRetenido);
            if (montoReembolsar.compareTo(BigDecimal.ZERO) < 0) {
                montoReembolsar = BigDecimal.ZERO;
            }
        }

        return new CompensacionVenta(null, venta, multaInsumos, comisionCocinero,
                comisionTransbank, totalRetenido, montoReembolsar,
                motivo, LocalDateTime.now());
    }
}

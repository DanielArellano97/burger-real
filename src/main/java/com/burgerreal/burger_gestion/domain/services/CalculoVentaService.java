package com.burgerreal.burger_gestion.domain.services;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoVentaService {

    /**
     * Calcula la comisión que cobra la pasarela (ej: Mercado Pago).
     */
    public Long calcularComision(Long montoBruto, MetodoPago metodoPago) {
        if (!metodoPago.estaActivo()) {
            return 0L;
        }

        // 1. Convertimos el montoBruto (Long) a BigDecimal para el cálculo
        BigDecimal montoBI = BigDecimal.valueOf(montoBruto);

        // 2. Calculamos la comisión variable: (Monto * Porcentaje / 100)
        BigDecimal comisionVariable = montoBI
                .multiply(metodoPago.porcentajeComision())
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        // 3. Sumamos la comisión fija (convertida a BigDecimal) y pasamos el resultado final a Long
        return comisionVariable
                .add(BigDecimal.valueOf(metodoPago.comisionFija()))
                .setScale(0, RoundingMode.HALF_UP) // Redondeamos al peso más cercano
                .longValue();
    }

    /**
     * Calcula la ganancia neta final.
     * Venta - Costos - Comisión
     */
    public Long calcularGananciaNeta(Long bruto, Long costos, Long comision) {
        return bruto - comision - costos;
    }
}

package com.burgerreal.burger_gestion.domain.services;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoVentaServices {

    /**
     * Calcula la comisión que cobra la pasarela (ej: Mercado Pago).
     */
    public BigDecimal calcularComision(BigDecimal montoBruto, MetodoPago metodoPago) {
        if (!metodoPago.estaActivo()) {
            return BigDecimal.ZERO;
        }

        // (Monto * Porcentaje / 100) + Comisión Fija
        BigDecimal comisionVariable = montoBruto
                .multiply(metodoPago.porcentajeComision())
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        return comisionVariable.add(metodoPago.comisionFija());
    }

    /**
     * Calcula la ganancia neta final.
     * Venta - Costos - Comisión
     */
    public BigDecimal calcularGananciaNeta(BigDecimal bruto, BigDecimal costos, BigDecimal comision) {
        return bruto.subtract(costos).subtract(comision);
    }
}

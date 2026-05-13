package com.burgerreal.burger_gestion.domain.services;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoVentaService {

    /**
     * Calcula la comisión que cobra la pasarela (ej: Mercado Pago).
     */
    public BigDecimal calcularComision(BigDecimal montoBruto, MetodoPago metodoPago) {
        // 1
        if (metodoPago == null || !metodoPago.estaActivo()) {
            return BigDecimal.ZERO;
        }

        // 2. Calculamos la comisión variable: (Monto * Porcentaje / 100)
        BigDecimal comisionVariable = montoBruto
                .multiply(metodoPago.porcentajeComision())
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

        // 3. Sumar la comisión fija
        // Esto es vital para apps de delivery o máquinas que cobran por evento (ej. $300 por transaccion)
        BigDecimal comisionFija = BigDecimal.valueOf(metodoPago.comisionFija());

        BigDecimal comisionTotal = comisionVariable.add(comisionFija);

        // 4. Retornamos con 4 decimales para que el cálculo de la ganancia neta sea exacto.
        // El redondeo al entero lo haremos recién en el objeto Venta.
        return comisionTotal.setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Calcula la ganancia neta final.
     * Venta - Costos - Comisión
     */
    public BigDecimal calcularGananciaNeta(BigDecimal bruto, BigDecimal costoInsumos, BigDecimal comision) {
        return bruto.subtract(costoInsumos).subtract(comision);
    }

    /**
     * Calcula la multa del 10% para el staff por el tiempo invertido.
     */
    public BigDecimal calcularMultaStaff(BigDecimal montoBruto) {
        // Si la venta es de $10.500, la multa es de $1.050,0000
        return montoBruto
                .multiply(new BigDecimal("0.10"))
                .setScale(4, RoundingMode.HALF_UP);
    }
}

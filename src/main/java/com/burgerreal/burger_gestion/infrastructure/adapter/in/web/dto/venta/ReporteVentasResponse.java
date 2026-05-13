package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

import java.math.BigDecimal;

public record ReporteVentasResponse(

        // 1. ENTRADAS (Dinero que ingresa)
        BigDecimal ingresosVentasBruto,    // Suma de montos brutos de ventas COMPLETADAS
        BigDecimal recuperacionInsumosAnulados, // Aquí irían los 5000
        BigDecimal ingresosMultasCocina,        // Aquí irían los 1500

        // 2. SALIDAS (Dinero que se va o se pierde)
        BigDecimal costoInsumosVendidos,   // Costo de insumos de ventas COMPLETADAS
        BigDecimal costoInsumosPerdidos,   // Costo de insumos de ventas ANULADAS (si estaban preparadas)
        BigDecimal comisionesBancariasTotales, // Comisiones de COMPLETADAS + ANULADAS (si hubo pago)

        // 3. BALANCE FINAL (La verdad del bolsillo)
        BigDecimal balanceRealNeto,        // (Ingresos Brutos + Multas) - (Todos los costos y comisiones)

        // 4. ESTADÍSTICAS OPERATIVAS
        int cantidadVentasCompletadas,
        int cantidadVentasAnuladas,
        double tiempoPromedioCocinaMinutos,
        double tiempoPromedioEsperaTotalMinutos
) {
}
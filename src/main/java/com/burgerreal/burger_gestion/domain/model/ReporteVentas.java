package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;

public record ReporteVentas(// 1. ENTRADAS (Dinero que ingresa)

    // 1. ENTRADAS (Dinero que ingresa)
    BigDecimal ingresosVentasBruto,
    BigDecimal recuperacionInsumosAnulados,
    BigDecimal ingresosMultasCocina,


    // 2. SALIDAS (Dinero que se va o se pierde)
    BigDecimal costoInsumosVendidos,
    BigDecimal costoInsumosPerdidos,
    BigDecimal comisionesBancariasTotales,

    // 3. BALANCE FINAL (La verdad del bolsillo)
    BigDecimal balanceRealNeto,       // (Ingresos Brutos + Multas) - (Todos los costos y comisiones)

    // 4. ESTADÍSTICAS OPERATIVAS
    int cantidadVentasCompletadas,
    int cantidadVentasAnuladas,
    double tiempoPromedioCocinaMinutos,
    double tiempoPromedioEsperaTotalMinutos
) {

    // Metodo estático para calcular el balance y crear la instancia
    public static ReporteVentas crearReporteVenta(
            BigDecimal ingresosBrutos,
            BigDecimal recuperacionInsumos,
            BigDecimal multasCocina,
            BigDecimal insumosVendidos,
            BigDecimal insumosPerdidos,
            BigDecimal comisiones,
            int completadas,
            int anuladas,
            long totalMinCocina,
            int conteoCocina,
            long totalMinEspera,
            int conteoEspera) {

        // Total Entradas = Bruto + Recuperación + Multas
        BigDecimal totalEntradas = ingresosBrutos
                .add(recuperacionInsumos)
                .add(multasCocina);

        // Total Salidas = Insumos Vendidos + Insumos Perdidos + Comisiones
        BigDecimal totalSalidas = insumosVendidos
                .add(insumosPerdidos)
                .add(comisiones);

        // Balance Neto Final
        BigDecimal balanceNeto = totalEntradas.subtract(totalSalidas);

        return new ReporteVentas(
                ingresosBrutos, recuperacionInsumos, multasCocina,
                insumosVendidos, insumosPerdidos, comisiones,
                balanceNeto, completadas, anuladas,
                (conteoCocina > 0 ? (double) totalMinCocina / conteoCocina : 0.0),
                (conteoEspera > 0 ? (double) totalMinEspera / conteoEspera : 0.0)
        );
    }

}

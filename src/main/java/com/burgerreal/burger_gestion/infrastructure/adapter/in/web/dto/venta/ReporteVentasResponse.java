package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

public record ReporteVentasResponse(
        // 1. ENTRADAS (Dinero que ingresa)
        long ingresosVentasBruto,    // Suma de montos brutos de ventas COMPLETADAS
        long recuperacionInsumosAnulados, // Aquí irían los 5000
        long ingresosMultasCocina,        // Aquí irían los 1500

        // 2. SALIDAS (Dinero que se va o se pierde)
        long costoInsumosVendidos,   // Costo de insumos de ventas COMPLETADAS
        long costoInsumosPerdidos,   // Costo de insumos de ventas ANULADAS (si estaban preparadas)
        long comisionesBancariasTotales, // Comisiones de COMPLETADAS + ANULADAS (si hubo pago)

        // 3. BALANCE FINAL (La verdad del bolsillo)
        long balanceRealNeto,        // (Ingresos Brutos + Multas) - (Todos los costos y comisiones)

        // 4. ESTADÍSTICAS OPERATIVAS
        int cantidadVentasCompletadas,
        int cantidadVentasAnuladas,
        double tiempoPromedioCocinaMinutos,
        double tiempoPromedioEsperaTotalMinutos
) {

    // Metodo estático para calcular el balance y crear la instancia
    public static ReporteVentasResponse of(
            long ingresosBrutos, long recuperacionInsumos, long multasCocina,
            long insumosVendidos, long insumosPerdidos, long comisiones,
            int completadas, int anuladas, long totalMinCocina, int conteoCocina,
            long totalMinEspera, int conteoEspera) {

        long totalEntradas = ingresosBrutos + recuperacionInsumos + multasCocina;
        long totalSalidas = insumosVendidos + insumosPerdidos + comisiones;
        long balanceNeto = totalEntradas - totalSalidas;

        return new ReporteVentasResponse(
                ingresosBrutos, recuperacionInsumos, multasCocina,
                insumosVendidos, insumosPerdidos, comisiones,
                balanceNeto, completadas, anuladas,
                (conteoCocina > 0 ? (double) totalMinCocina / conteoCocina : 0.0),
                (conteoEspera > 0 ? (double) totalMinEspera / conteoEspera : 0.0)
        );
    }

}
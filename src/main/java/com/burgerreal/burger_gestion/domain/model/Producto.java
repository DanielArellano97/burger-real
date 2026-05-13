package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record Producto(
        Long id,
        String nombre,
        String descripcion,
        Long precioVenta,
        BigDecimal costoProduccionTotal,
        String imagenUrl,
        boolean disponible,
        List<ProductoInsumo> ingredientes // La lista de insumos que componen la receta
) {

    public static Producto crearProducto(String nombre, String descripcion, Long precioVenta,
                           String imagenUrl, boolean disponible, List<ProductoInsumo> ingredientes){

        // 1. Calculamos el costo antes de crear la instancia
        BigDecimal costoCalculado = calcularCostoProduccion(ingredientes);

        return new Producto(
                null,
                nombre,
                descripcion,
                precioVenta,
                costoCalculado,
                imagenUrl,
                disponible,
                ingredientes
        );
    }

    /**
     * Lógica de Negocio: Calcula cuánto nos cuesta producir este producto
     * sumando el costo de cada insumo por la cantidad usada.
     */
    private static BigDecimal calcularCostoProduccion(List<ProductoInsumo> ingredientes) {
        // Calculamos con toda la precisión de BigDecimal
        BigDecimal costoExacto = ingredientes.stream()
                .map(i -> i.insumo().costoUnitario().multiply(BigDecimal.valueOf(i.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lógica para redondear hacia arriba a la centena más cercana
        // Ejemplo: 2918.55 -> 3000
        // Dividimos por 100, redondeamos hacia arriba (CEILING) y multiplicamos por 100

        return costoExacto
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Lógica de Negocio: Calcula la ganancia neta por cada venta
     */
    public Long calcularMargenGanancia() {
        // precioVenta ya es Long, calcularCostoProduccion devuelve Long.
        return precioVenta - costoProduccionTotal.longValue();
    }
}

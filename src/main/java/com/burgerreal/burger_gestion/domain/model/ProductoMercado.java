package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ProductoMercado(
        Long id,
        String nombre,
        Double valorCompra,
        Double cantidadTotal,
        String unidadMedida
) {

    // Metodo para cuando creas algo nuevo (sin ID)
    public static ProductoMercado crearNuevo(String nombre, Double valorCompra, Double cantidadTotal, String unidadMedida) {
        return new ProductoMercado(
                null, // El ID nace nulo para que JPA lo genere
                nombre,
                valorCompra,
                cantidadTotal,
                unidadMedida
        );
    }

    // Metodo para mapper solo con id
    public static ProductoMercado crearNuevoSoloId(Long id) {
        return new ProductoMercado(
                id,
                null,
                null,
                null,
                null
        );
    }

    public BigDecimal getPrecioPorUnidadMinima() {
        if (this.unidadMedida == null || this.unidadMedida.isBlank()) {
            throw new IllegalStateException("El producto " + this.nombre + " no tiene unidad de medida.");
        }

        BigDecimal factorConversion = BigDecimal.ONE;
        if (this.unidadMedida.equalsIgnoreCase("KG") || this.unidadMedida.equalsIgnoreCase("L")) {
            factorConversion = BigDecimal.valueOf(1000);
        }

        // valorCompra / (cantidadTotal * factorConversion)
        BigDecimal divisor = BigDecimal.valueOf(this.cantidadTotal).multiply(factorConversion);

        return BigDecimal.valueOf(this.valorCompra)
                .divide(divisor, 4, RoundingMode.HALF_UP);
    }
}

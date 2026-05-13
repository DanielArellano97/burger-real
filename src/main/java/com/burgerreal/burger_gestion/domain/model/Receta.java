package com.burgerreal.burger_gestion.domain.model;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record Receta(
        Long id,
        String nombreInsumoFinal,
        CategoriaInsumo categoriaInsumo,
        String unidadMedida,
        double rendimiento,
        double factorGas,
        double factorAceite,
        List<RecetaIngredientes> ingredientes
) {

    public static Receta crearReceta(String nombreInsumoFinal, CategoriaInsumo categoriaInsumo, String unidadMedida, double rendimiento, double factorGas, double factorAceite, List<RecetaIngredientes> ingredientes){
        return new Receta(
                null,
                nombreInsumoFinal,
                categoriaInsumo,
                unidadMedida,
                rendimiento,
                factorGas,
                factorAceite,
                ingredientes
        );
    }

    //Constructor para crear receta con id para el registro del insumo.
    public static Receta crearReceta(Long id, String nombreInsumoFinal, CategoriaInsumo categoriaInsumo, String unidadMedida, double rendimiento, double factorGas, double factorAceite, List<RecetaIngredientes> ingredientes){
        return new Receta(
                id,
                nombreInsumoFinal,
                categoriaInsumo,
                unidadMedida,
                rendimiento,
                factorGas,
                factorAceite,
                ingredientes
        );
    }

    public BigDecimal calcularCostoFinal(BigDecimal precioGramoGas, BigDecimal precioMlAceite) {
        BigDecimal costoIngredientes = ingredientes.stream()
                .map(ing -> ing.productoMercado().getPrecioPorUnidadMinima()
                        .multiply(BigDecimal.valueOf(ing.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costoEnergia = precioGramoGas.multiply(BigDecimal.valueOf(factorGas))
                .add(precioMlAceite.multiply(BigDecimal.valueOf(factorAceite)));

        return costoIngredientes.add(costoEnergia)
                .divide(BigDecimal.valueOf(rendimiento), 4, RoundingMode.HALF_UP);
    }
}

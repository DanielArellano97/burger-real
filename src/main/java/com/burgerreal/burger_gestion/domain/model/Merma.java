package com.burgerreal.burger_gestion.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record Merma(
        Long id,
        Long insumoId,
        String nombreInsumo, // Para histórico, por si el insumo se borra
        Double cantidad,
        String motivo,
        LocalDateTime fecha,
        BigDecimal costoPerdido // cantidad * costoUnitario del insumo en ese momento
) {
    public static Merma crear(Long insumoId, String nombreInsumo, Double cantidad, String motivo, BigDecimal costoUnitario) {

        BigDecimal costoPerdido = costoUnitario
                .multiply(BigDecimal.valueOf(cantidad))
                .setScale(4, RoundingMode.HALF_UP);

        return new Merma(
                null,
                insumoId,
                nombreInsumo,
                cantidad,
                motivo,
                LocalDateTime.now(),
                costoPerdido
        );
    }
}

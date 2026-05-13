package com.burgerreal.burger_gestion.application.service;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;

public class ProduccionService {

    private final ProductoMercadoRepositoryPort parametroMercadoRepository;

    // Constantes para no usar IDs
    private static final String CONST_GAS = "GAS";
    private static final String CONST_ACEITE = "ACEITE";

    public ProduccionService(ProductoMercadoRepositoryPort parametroMercadoRepository) {
        this.parametroMercadoRepository = parametroMercadoRepository;
    }

    public BigDecimal calcularCostoPorGramo(Receta receta) {
        // Inicializamos con ZERO de BigDecimal
        BigDecimal precioGas = BigDecimal.ZERO;
        BigDecimal precioAceite = BigDecimal.ZERO;

        // 2. Recuperar precios de mercado
        if (receta.factorGas() > 0) {
            precioGas = parametroMercadoRepository.buscarPorNombre(CONST_GAS)
                    .map(ProductoMercado::getPrecioPorUnidadMinima)
                    .orElseThrow(() -> new EntityNotFoundException("El parametro de mercado " + CONST_GAS + " no existe"));
        }

        if (receta.factorAceite() > 0) {
            precioAceite = parametroMercadoRepository.buscarPorNombre(CONST_ACEITE)
                    .map(ProductoMercado::getPrecioPorUnidadMinima)
                    .orElseThrow(() -> new EntityNotFoundException("El parametro de mercado " + CONST_ACEITE + " no existe"));
        }

        // 3. Delegar el cálculo al objeto de dominio
        // Ahora devuelve un BigDecimal con la precisión de 4 decimales que definimos
        return receta.calcularCostoFinal(precioGas, precioAceite);
    }
}

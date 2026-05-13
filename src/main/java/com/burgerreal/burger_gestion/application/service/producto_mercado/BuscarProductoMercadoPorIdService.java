package com.burgerreal.burger_gestion.application.service.producto_mercado;

import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.BuscarProductoMercadoPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;

public class BuscarProductoMercadoPorIdService implements BuscarProductoMercadoPorIdUseCase {

    private final ProductoMercadoRepositoryPort productoMercadoRepositoryPort;

    public BuscarProductoMercadoPorIdService(ProductoMercadoRepositoryPort productoMercadoRepositoryPort) {
        this.productoMercadoRepositoryPort = productoMercadoRepositoryPort;
    }

    @Override
    public ProductoMercado ejecutar(Long id) {
        return productoMercadoRepositoryPort.obtenerPorId(id).orElseThrow(() -> new EntityNotFoundException("Parametro mercado con ID: " + id + " no fue encontrado"));
    }
}

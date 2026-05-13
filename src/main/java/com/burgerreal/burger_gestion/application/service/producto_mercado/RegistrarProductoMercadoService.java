package com.burgerreal.burger_gestion.application.service.producto_mercado;

import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.RegistrarProductoMercadoUseCase;
import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;

public class RegistrarProductoMercadoService implements RegistrarProductoMercadoUseCase {

    private final ProductoMercadoRepositoryPort parametroMercadoRepositoryPort;

    public RegistrarProductoMercadoService(ProductoMercadoRepositoryPort parametroMercadoRepositoryPort) {
        this.parametroMercadoRepositoryPort = parametroMercadoRepositoryPort;
    }

    @Override
    public ProductoMercado ejecutar(ProductoMercado parametroMercado) {
        return parametroMercadoRepositoryPort.guardar(parametroMercado);
    }
}

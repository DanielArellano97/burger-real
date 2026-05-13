package com.burgerreal.burger_gestion.application.service.producto_mercado;

import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.ListarParametrosMercadoUseCase;
import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;

import java.util.List;

public class ListarParametrosMercadoService implements ListarParametrosMercadoUseCase {

    private final ProductoMercadoRepositoryPort productoMercadoRepositoryPort;

    public ListarParametrosMercadoService(ProductoMercadoRepositoryPort parametroMercadoRepositoryPort) {
        this.productoMercadoRepositoryPort = parametroMercadoRepositoryPort;
    }

    @Override
    public List<ProductoMercado> ejecutar() {
        return productoMercadoRepositoryPort.listar();
    }
}

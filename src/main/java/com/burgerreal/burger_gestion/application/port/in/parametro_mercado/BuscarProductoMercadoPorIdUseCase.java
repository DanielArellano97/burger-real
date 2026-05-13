package com.burgerreal.burger_gestion.application.port.in.parametro_mercado;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;

public interface BuscarProductoMercadoPorIdUseCase {

    ProductoMercado ejecutar(Long id);
}

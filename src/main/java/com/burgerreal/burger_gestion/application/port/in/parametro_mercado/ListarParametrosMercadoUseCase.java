package com.burgerreal.burger_gestion.application.port.in.parametro_mercado;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;

import java.util.List;

public interface ListarParametrosMercadoUseCase {

    List<ProductoMercado> ejecutar();
}

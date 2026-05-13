package com.burgerreal.burger_gestion.application.port.in.producto;

import com.burgerreal.burger_gestion.domain.model.Producto;

import java.util.List;

public interface ListarProductosUseCase {

    List<Producto> ejecutar();
}

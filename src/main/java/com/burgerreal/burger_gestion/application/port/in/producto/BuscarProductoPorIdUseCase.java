package com.burgerreal.burger_gestion.application.port.in.producto;

import com.burgerreal.burger_gestion.domain.model.Producto;

public interface BuscarProductoPorIdUseCase {

    Producto ejecutar(Long id);
}

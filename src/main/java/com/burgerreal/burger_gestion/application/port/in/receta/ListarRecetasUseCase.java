package com.burgerreal.burger_gestion.application.port.in.receta;

import com.burgerreal.burger_gestion.domain.model.Receta;

import java.util.List;

public interface ListarRecetasUseCase {

    List<Receta> ejecutar();
}

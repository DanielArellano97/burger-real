package com.burgerreal.burger_gestion.application.port.in.insumo;

import com.burgerreal.burger_gestion.domain.model.Insumo;

import java.util.List;

public interface ListarInsumosUseCase {

    List<Insumo> ejecutar();
}

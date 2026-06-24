package com.burgerreal.burger_gestion.application.port.in.combo;

import com.burgerreal.burger_gestion.domain.model.Combo;

import java.util.List;

public interface ListarCombosUseCase {
    List<Combo> ejecutar();
}

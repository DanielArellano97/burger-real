package com.burgerreal.burger_gestion.application.port.in.merma;

import com.burgerreal.burger_gestion.domain.model.Merma;

import java.util.List;

public interface ListarMermasUseCase {

    List<Merma> ejecutar();
}

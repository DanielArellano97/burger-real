package com.burgerreal.burger_gestion.application.port.in.receta;

import com.burgerreal.burger_gestion.domain.model.Receta;

public interface RegistrarRecetaUseCase {

    Receta ejecutar(Receta receta);
}

package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Receta;

import java.util.List;
import java.util.Optional;

public interface RecetaRepositoryPort {
    Receta registrar(Receta receta);
    List<Receta> listar();
    Optional<Receta> buscarPorId(Long id);

}

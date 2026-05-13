package com.burgerreal.burger_gestion.application.service.receta;

import com.burgerreal.burger_gestion.application.port.in.receta.BuscarRecetaPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.port.out.RecetaRepositoryPort;
import jakarta.persistence.EntityNotFoundException;

public class BuscarRecetaPorIdService implements BuscarRecetaPorIdUseCase {

    private final RecetaRepositoryPort recetaRepositoryPort;

    public BuscarRecetaPorIdService(RecetaRepositoryPort recetaRepositoryPort) {
        this.recetaRepositoryPort = recetaRepositoryPort;
    }

    @Override
    public Receta ejecutar(Long id) {
        return recetaRepositoryPort.buscarPorId(id).
                orElseThrow(() -> new EntityNotFoundException("La receta con ID: " + id + " no existe"));
    }
}

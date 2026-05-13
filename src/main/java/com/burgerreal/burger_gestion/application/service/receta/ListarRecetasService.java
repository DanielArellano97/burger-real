package com.burgerreal.burger_gestion.application.service.receta;

import com.burgerreal.burger_gestion.application.port.in.receta.ListarRecetasUseCase;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.port.out.RecetaRepositoryPort;

import java.util.List;

public class ListarRecetasService implements ListarRecetasUseCase {

    private final RecetaRepositoryPort recetaRepositoryPort;

    public ListarRecetasService(RecetaRepositoryPort recetaRepositoryPort) {
        this.recetaRepositoryPort = recetaRepositoryPort;
    }

    @Override
    public List<Receta> ejecutar() {
        return recetaRepositoryPort.listar();
    }
}

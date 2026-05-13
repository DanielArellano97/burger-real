package com.burgerreal.burger_gestion.application.service.insumo;

import com.burgerreal.burger_gestion.application.port.in.insumo.ListarInsumosUseCase;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;

import java.util.List;

public class ListarInsumosService implements ListarInsumosUseCase {

    private final InsumoRepositoryPort insumoRepositoryPort;

    public ListarInsumosService(InsumoRepositoryPort insumoRepositoryPort) {
        this.insumoRepositoryPort = insumoRepositoryPort;
    }

    @Override
    public List<Insumo> ejecutar() {
        return insumoRepositoryPort.listarTodos();
    }
}

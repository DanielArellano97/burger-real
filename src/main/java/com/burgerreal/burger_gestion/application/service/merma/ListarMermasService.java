package com.burgerreal.burger_gestion.application.service.merma;

import com.burgerreal.burger_gestion.application.port.in.merma.ListarMermasUseCase;
import com.burgerreal.burger_gestion.domain.model.Merma;
import com.burgerreal.burger_gestion.domain.port.out.MermaRepositoryPort;

import java.util.List;

public class ListarMermasService implements ListarMermasUseCase {

    private final MermaRepositoryPort mermaRepositoryPort;

    public ListarMermasService(MermaRepositoryPort mermaRepositoryPort) {
        this.mermaRepositoryPort = mermaRepositoryPort;
    }

    @Override
    public List<Merma> ejecutar() {
        return mermaRepositoryPort.buscarTodas();
    }
}

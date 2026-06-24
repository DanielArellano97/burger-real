package com.burgerreal.burger_gestion.application.service.combo;

import com.burgerreal.burger_gestion.application.port.in.combo.ListarCombosUseCase;
import com.burgerreal.burger_gestion.domain.model.Combo;
import com.burgerreal.burger_gestion.domain.port.out.ComboRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ListarCombosService implements ListarCombosUseCase {

    private final ComboRepositoryPort comboRepositoryPort;

    public ListarCombosService(ComboRepositoryPort comboRepositoryPort) {
        this.comboRepositoryPort = comboRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Combo> ejecutar() {
        return comboRepositoryPort.listarTodos();
    }
}

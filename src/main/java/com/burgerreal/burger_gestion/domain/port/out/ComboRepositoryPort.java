package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Combo;

import java.util.List;

public interface ComboRepositoryPort {
    List<Combo> listarTodos();
}

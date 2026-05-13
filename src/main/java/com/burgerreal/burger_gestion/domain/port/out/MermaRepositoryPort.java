package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Merma;

import java.util.List;

public interface MermaRepositoryPort {
    Merma guardar(Merma merma);
    List<Merma> buscarTodas();
}
package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoRepositoryPort {
    Insumo guardar(Insumo insumo);
    Optional<Insumo> buscarPorId(Long id);
    List<Insumo> listarTodos();
    void eliminar(Long id);
    boolean existePorNombre(String nombre);
}

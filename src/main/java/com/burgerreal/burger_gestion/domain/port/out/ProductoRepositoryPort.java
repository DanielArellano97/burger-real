package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {

    Producto guardar(Producto producto);
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Long id);
    void eliminar(Long id);
}

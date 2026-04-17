package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;

import java.util.List;
import java.util.Optional;

public interface MetodoPagoRepositoryPort {

    Optional<MetodoPago> buscarPorId(Long id);
    List<MetodoPago> obtenerTodosLosActivos();
}

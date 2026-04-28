package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;

import java.util.Optional;

public interface CompensacionVentaRepositoryPort {

    CompensacionVenta guardar(CompensacionVenta compensacionVenta);
    Optional<CompensacionVenta> buscarCompensacionVentaPorVentaId(Long id);
}

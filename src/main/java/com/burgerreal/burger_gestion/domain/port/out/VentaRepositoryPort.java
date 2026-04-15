package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.Venta;

import java.util.List;

public interface VentaRepositoryPort {

    Venta guardar(Venta venta);
    List<Venta> obtenerTodas();
}

package com.burgerreal.burger_gestion.application.port.in.producto;

import com.burgerreal.burger_gestion.domain.model.Producto;

import java.util.List;

public interface RegistrarProductoUseCase {

    record Command(
            String nombre,
            String descripcion,
            Long precioVenta,
            String imagenUrl,
            List<ItemReceta> ingredientes
    ){}

    record ItemReceta(
            Long insumoId,
            Double cantidad
    ){}

    Producto ejecutar(Command command);
}

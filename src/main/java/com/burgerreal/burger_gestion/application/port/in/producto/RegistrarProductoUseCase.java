package com.burgerreal.burger_gestion.application.port.in.producto;

import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import com.burgerreal.burger_gestion.domain.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface RegistrarProductoUseCase {

    record Command(
            String nombre,
            String descripcion,
            Long precioVenta,
            Long precioOferta,
            String imagenUrl,
            boolean disponible,
            boolean requiereCocina,
            CategoriaProducto categoria,
            List<ItemReceta> ingredientes,
            List<VarianteInput> variantes
    ){}

    record ItemReceta(
            Long insumoId,
            Double cantidad,
            boolean permiteQuitar,
            boolean permiteAgregar
    ){}

    // 🌟 Nuevo record auxiliar para capturar las variantes desde el Front/Controller
    record VarianteInput(
            String nombre,      // Ej: "x5 unidades"
            String nombreCorto, // Ej: "x5"
            BigDecimal precioExtra // Ej: 0
    ){}

    Producto ejecutar(Command command);
}

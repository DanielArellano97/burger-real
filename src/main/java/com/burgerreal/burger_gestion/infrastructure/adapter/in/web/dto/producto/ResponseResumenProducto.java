package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto;

import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import java.math.BigDecimal;
import java.util.List;

public record ResponseResumenProducto(
        Long id,
        String nombre,
        String descripcion,
        Long precioVenta,
        Long precioOferta,
        String imagenUrl,
        CategoriaProducto categoria,
        boolean disponible,
        List<IngredienteResumenDTO> ingredientes,
        List<VarianteResumenDTO> variantes // 🌟 Ahora viaja el objeto con id y precio
) {
    // 🍔 Nested Record para la data rica del ingrediente en el catálogo/modal
    public record IngredienteResumenDTO(
            Long id,
            String nombre,
            BigDecimal precioExtra,
            boolean permiteQuitar,
            boolean permiteAgregar
    ) {}

    // 🍟 Nested Record para controlar las variantes de tamaño (x5, x10, x15, etc.)
    public record VarianteResumenDTO(
            Long id,
            String nombre,      // Ej: "x5 unidades"
            String nombreCorto, // Ej: "x5"
            BigDecimal precioExtra // Ej: 2500
    ) {}
}
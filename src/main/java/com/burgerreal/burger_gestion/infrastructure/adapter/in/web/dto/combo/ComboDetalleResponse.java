package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.combo;

import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;

import java.util.List;

public record ComboDetalleResponse(
        Long productoId,
        String productoNombre,
        Integer cantidad,
        Boolean requiereSeleccion,
        ResponseResumenProducto.VarianteResumenDTO varianteSeleccionada, // Si es fija (ej: aritos x10)
        List<ResponseResumenProducto.VarianteResumenDTO> opcionesDisponibles// Si requiereSeleccion es true (ej: sabores de bebida)
) {
}

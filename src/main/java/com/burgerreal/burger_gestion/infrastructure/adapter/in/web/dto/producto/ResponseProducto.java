package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto;

import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.ResponseProductoInsumo;

import java.math.BigDecimal;
import java.util.List;

public record ResponseProducto(
        Long id,
        String nombre,
        String descripcion,
        Long precioVenta,
        Long precioOferta,
        BigDecimal costoProduccionTotal,
        String imagenUrl,
        boolean disponible,
        CategoriaProducto categoria,
        List<ResponseProductoInsumo> ingredientes
) {
}

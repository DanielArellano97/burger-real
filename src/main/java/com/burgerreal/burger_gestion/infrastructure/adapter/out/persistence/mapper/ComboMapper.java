package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.Combo;
import com.burgerreal.burger_gestion.domain.model.ComboDetalle;
import com.burgerreal.burger_gestion.domain.model.ProductoVariante;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ComboDetalleEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ComboEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComboMapper {

    // 🌟 Inyectamos los mappers que ya tienes listos del catálogo base
    private final ProductoMapper productoMapper;

    public ComboMapper(ProductoMapper productoMapper) {
        this.productoMapper = productoMapper;
    }

    // Asumo que tienes mappers para Producto y Variante inyectados o accesibles si fuesen necesarios,
    // pero acá lo mapeamos de manera directa al Record estructural.

    public Combo toDomain(ComboEntity entity) {
        if (entity == null) return null;

        List<ComboDetalle> detalles = entity.getDetalles().stream()
                .map(this::toDetalleDomain)
                .collect(Collectors.toList());

        return new Combo(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecioCombo(),
                entity.getDisponible(),
                detalles
        );
    }

    private ComboDetalle toDetalleDomain(ComboDetalleEntity entity) {
        if (entity == null) return null;

        // 1. Reutilizamos tu mapper para transformar el ProductoEntity a Producto de dominio
        var productoDominio = productoMapper.toDominio(entity.getProducto());

        // 2. Mapeamos manualmente la variante fija si existe (ej: Aritos x10)
        // Pasamos el precioExtra a BigDecimal ya que tu entidad probablemente usa Integer o Long
        ProductoVariante varianteDominio = null;
        if (entity.getVarianteFija() != null) {
            varianteDominio = new ProductoVariante(
                    entity.getVarianteFija().getId(),
                    entity.getVarianteFija().getNombre(),
                    entity.getVarianteFija().getNombreCorto(),
                    entity.getVarianteFija().getPrecioExtra()
            );
        }

        // 3. Retornamos el record de dominio puro
        return new ComboDetalle(
                entity.getId(),
                productoDominio,
                varianteDominio,
                entity.getCantidad()
        );
    }
}

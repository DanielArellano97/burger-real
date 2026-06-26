package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;
import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.model.ProductoInsumo;
import com.burgerreal.burger_gestion.domain.model.ProductoVariante;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoVarianteEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductoMapper {

    private final ProductoInsumoMapper productoInsumoMapper;

    public ProductoMapper(ProductoInsumoMapper productoInsumoMapper) {
        this.productoInsumoMapper = productoInsumoMapper;
    }

    public ProductoEntity toEntity(Producto dominio){
        if(dominio == null) return null;

        ProductoEntity entity = new ProductoEntity();

        if (dominio.id() != null) {
            entity.setId(dominio.id());
        }

        entity.setNombre(dominio.nombre());
        entity.setDescripcion(dominio.descripcion());
        entity.setPrecioVenta(dominio.precioVenta());
        entity.setPrecioOferta(dominio.precioOferta());
        entity.setCostoProduccionTotal(dominio.costoProduccionTotal());
        entity.setImagenUrl(dominio.imagenUrl());
        entity.setDisponible(dominio.disponible());
        entity.setRequiereCocina(dominio.requiereCocina());
        entity.setCategoria(dominio.categoria());

        entity.setIngredientes(dominio.ingredientes() != null
                ? dominio.ingredientes().stream().map(productoInsumoMapper::toEntity).toList()
                : Collections.emptyList());

        if (dominio.variantes() != null) {
            List<ProductoVarianteEntity> varianteEntities = dominio.variantes().stream()
                    .map(v -> {
                        ProductoVarianteEntity ve = toVarianteEntity(v);
                        ve.setProducto(entity);
                        return ve;
                    }).toList();
            entity.setVariantes(varianteEntities);
        } else {
            entity.setVariantes(Collections.emptyList());
        }

        return entity;
    }

    public Producto toDominio(ProductoEntity entity){
        if(entity == null) return null;

        return new Producto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecioVenta(),
                entity.getPrecioOferta(),
                entity.getCostoProduccionTotal(),
                entity.getImagenUrl(),
                entity.isDisponible(),
                entity.isRequiereCocina(),
                entity.getCategoria(),
                entity.getIngredientes() != null
                        ? entity.getIngredientes().stream().map(productoInsumoMapper::toDominio).toList()
                        : Collections.emptyList(),
                entity.getVariantes() != null
                        ? entity.getVariantes().stream().map(this::toVarianteDominio).toList()
                        : Collections.emptyList()
        );
    }

    public List<ResponseResumenProducto> toResponseResumenProducto(List<Producto> dominio) {
        if (dominio == null) return Collections.emptyList();

        return dominio.stream()
                .map(p -> {
                    List<ResponseResumenProducto.IngredienteResumenDTO> ingredientesDTO =
                            (p.categoria() == CategoriaProducto.HAMBURGUESA)
                                    ? mapearIngredientes(p.ingredientes())
                                    : Collections.emptyList();

                    List<ResponseResumenProducto.VarianteResumenDTO> variantesDTO =
                            (p.variantes() != null)
                                    ? p.variantes().stream().map(this::mapearVarianteDTO).toList()
                                    : Collections.emptyList();

                    return new ResponseResumenProducto(
                            p.id(),
                            p.nombre(),
                            p.descripcion(),
                            p.precioVenta(),
                            p.precioOferta(),
                            p.imagenUrl(),
                            p.categoria(),
                            p.disponible(),
                            ingredientesDTO,
                            variantesDTO
                    );
                })
                .toList();
    }

    /**
     * 🌟 SE LIMPIÓ EL MALDITO: Ahora sólo transporta los booleanos puros
     * que configuraste directamente en tu base de datos.
     */
    private List<ResponseResumenProducto.IngredienteResumenDTO> mapearIngredientes(List<ProductoInsumo> ingredientes) {
        if (ingredientes == null) return Collections.emptyList();

        return ingredientes.stream()
                .filter(i -> i.insumo().categoria() != CategoriaInsumo.PACKAGING)
                .map(i -> new ResponseResumenProducto.IngredienteResumenDTO(
                        i.insumo().id(),
                        i.insumo().nombre(),
                        i.insumo().valorExtra(),
                        i.permiteQuitar(),  // 🌟 Directo del Dominio
                        i.permiteAgregar() // 🌟 Directo del Dominio
                ))
                .toList();
    }

    private ResponseResumenProducto.VarianteResumenDTO mapearVarianteDTO(ProductoVariante v) {
        return new ResponseResumenProducto.VarianteResumenDTO(
                v.id(),
                v.nombre(),
                v.nombreCorto(),
                v.precioExtra()
        );
    }

    private ProductoVarianteEntity toVarianteEntity(ProductoVariante dominio) {
        if (dominio == null) return null;
        return new ProductoVarianteEntity(
                dominio.id(),
                dominio.nombre(),
                dominio.nombreCorto(),
                dominio.precioExtra());
    }

    private ProductoVariante toVarianteDominio(ProductoVarianteEntity entity) {
        if (entity == null) return null;
        return new ProductoVariante(
                entity.getId(),
                entity.getNombre(),
                entity.getNombreCorto(),
                entity.getPrecioExtra()
        );
    }

    public ResponseProducto toResponseProducto(Producto dominio) {
        if (dominio == null) return null;

        return new ResponseProducto(
                dominio.id(),
                dominio.nombre(),
                dominio.descripcion(),
                dominio.precioVenta(),
                dominio.precioOferta(),
                dominio.costoProduccionTotal(),
                dominio.imagenUrl(),
                dominio.disponible(),
                dominio.categoria(),
                dominio.ingredientes().stream().map(productoInsumoMapper::toResponse).toList()
        );
    }

    public List<Producto> toDominioList(List<ProductoEntity> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(this::toDominio)
                .toList();
    }
}
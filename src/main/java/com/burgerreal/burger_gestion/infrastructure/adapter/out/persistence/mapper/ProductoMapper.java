package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
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

        // Si no viene con id significa que es una insercion
        if (dominio.id() == null) {
            // Caso: Venta Nueva (Sin ID)
            return new ProductoEntity(
                    dominio.nombre(),
                    dominio.descripcion(),
                    dominio.precioVenta(),
                    dominio.costoProduccionTotal(),
                    dominio.imagenUrl(),
                    dominio.disponible(),
                    dominio.ingredientes().stream().map(productoInsumoMapper::toEntity).toList()
            );
        } else {
            // Caso: Venta Existente (Con ID y Fecha)
            return new ProductoEntity(
                    dominio.id(),
                    dominio.nombre(),
                    dominio.descripcion(),
                    dominio.precioVenta(),
                    dominio.costoProduccionTotal(),
                    dominio.imagenUrl(),
                    dominio.disponible(),
                    dominio.ingredientes().stream().map(productoInsumoMapper::toEntity).toList()

            );
        }
    }



    public Producto toDominio(ProductoEntity entity){
        if(entity == null) return null;

        return new Producto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecioVenta(),
                entity.getCostoProduccionTotal(),
                entity.getImagenUrl(),
                entity.isDisponible(),
                entity.getIngredientes().stream().map(productoInsumoMapper::toDominio).toList()
        );
    }


    public List<ResponseResumenProducto> toResponseResumenProducto(List<Producto> dominio) {

        if (dominio == null) return Collections.emptyList(); // O null, según prefieras

        return dominio.stream()
                .map(p -> new ResponseResumenProducto(
                        p.id(),
                        p.nombre(),
                        p.descripcion(),
                        p.precioVenta(),
                        p.costoProduccionTotal().longValue(),
                        p.imagenUrl(),
                        p.disponible()
                ))
                .toList();
    }


    public ResponseProducto toResponseProducto(Producto dominio) {
        if (dominio == null) return null;

        return new ResponseProducto(
                dominio.id(),
                dominio.nombre(),
                dominio.descripcion(),
                dominio.precioVenta(),
                dominio.costoProduccionTotal(),
                dominio.imagenUrl(),
                dominio.disponible(),
                dominio.ingredientes().stream().map(productoInsumoMapper::toResponse).toList()
        );
    }

    public List<Producto> toDominioList(List<ProductoEntity> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(this::toDominio) // Reutiliza el metodo que ya escribiste
                .toList();
    }
}

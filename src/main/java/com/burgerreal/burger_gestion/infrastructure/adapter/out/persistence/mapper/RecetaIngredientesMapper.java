package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.RecetaIngredientes;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoMercadoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaIngredientesEntity;
import org.springframework.stereotype.Component;

@Component
public class RecetaIngredientesMapper {

    private final ProductoMercadoMapper productoMercadoMapper;

    public RecetaIngredientesMapper(ProductoMercadoMapper parametroMercadoMapper) {
        this.productoMercadoMapper = parametroMercadoMapper;
    }

    public RecetaIngredientes toDominio(RecetaIngredientesEntity entity){
        if(entity == null) return null;

        return new RecetaIngredientes(
                entity.getId(),
                productoMercadoMapper.toDominio(entity.getProductoMercado()),
                entity.getCantidad()
        );
    }

    public RecetaIngredientesEntity toEntity(RecetaIngredientes dominio, RecetaEntity entity){
        if (dominio == null) return null;

        ProductoMercadoEntity productoEntity = new ProductoMercadoEntity(dominio.productoMercado().id());

        return new RecetaIngredientesEntity(
                entity,
                productoEntity,
                dominio.cantidad()
        );
    }
}

package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.ProductoInsumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.ResponseProductoInsumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.InsumoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoInsumoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoInsumoMapper {

    private final  InsumoMapper insumoMapper;

    public ProductoInsumoMapper(InsumoMapper insumoMapper) {
        this.insumoMapper = insumoMapper;
    }

        public ProductoInsumoEntity toEntity(ProductoInsumo dominio){
        if(dominio == null) return null;

        // Convertimos el insumo de dominio a Entity (esto nos trae el ID)
        InsumoEntity insumoEntity = insumoMapper.toEntityParaProducto(dominio.insumo().id());

        //Creacion
        if(dominio.id() == null){
            return new ProductoInsumoEntity(
                    insumoEntity,
                    dominio.cantidad());
        }else{
            return new ProductoInsumoEntity(
                    dominio.id(),
                    insumoEntity,
                    dominio.cantidad());
        }

    }

    public ProductoInsumo toDominio(ProductoInsumoEntity entity){
        if(entity == null) return null;

        return new ProductoInsumo(
                entity.getId(),
                insumoMapper.toDominio(entity.getInsumo()),
                entity.getCantidad()
        );
    }

    public ResponseProductoInsumo toResponse(ProductoInsumo dominio){
        if(dominio == null) return null;

        return new ResponseProductoInsumo(
                dominio.id(),
                insumoMapper.toResponseResumenInsumo(dominio.insumo()),
                dominio.cantidad()
        );
    }
}

package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado.RequestParametroMercado;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado.ResponseParametroMercadoDetalle;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoMercadoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMercadoMapper {

    public ProductoMercado toDominio(ProductoMercadoEntity entity){
        if(entity == null) return null;

        return new ProductoMercado(
                entity.getId(),
                entity.getNombre(),
                entity.getValorCompra(),
                entity.getCantidadTotal(),
                entity.getUnidadMedida()
        );
    }

    public ProductoMercadoEntity toEntity(ProductoMercado dominio){
        if(dominio == null) return null;

        return new ProductoMercadoEntity(
                dominio.id(),
                dominio.nombre(),
                dominio.valorCompra(),
                dominio.cantidadTotal(),
                dominio.unidadMedida()
        );
    }

    public ProductoMercado toDominio(RequestParametroMercado request){
        if(request == null) return null;

        return ProductoMercado.crearNuevo(
                request.nombre(),
                request.valorCompra(),
                request.cantidadTotal(),
                request.unidadMedida()
        );
    }

    public ResponseParametroMercadoDetalle toResponseDetalle(ProductoMercado dominio){
        return new ResponseParametroMercadoDetalle(
                dominio.id(),
                dominio.nombre(),
                dominio.valorCompra(),
                dominio.cantidadTotal(),
                dominio.unidadMedida()
        );
    }
}

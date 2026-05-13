package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.CompensacionVentaEntity;
import org.springframework.stereotype.Component;

@Component
public class CompensacionMapper {

    private final VentaMapper ventaMapper;

    public CompensacionMapper(VentaMapper ventaMapper) {
        this.ventaMapper = ventaMapper;
    }

    public CompensacionVenta mapearEntityADominio(CompensacionVentaEntity entity){
        if(entity == null) return null;
        return new CompensacionVenta(
                entity.getId(),
                ventaMapper.mapearADomain(entity.getVenta()),
                entity.getCostoInsumos(),
                entity.getComisionCocina(),
                entity.getComisionTransbank(),
                entity.getMontoTotalRetenido(),
                entity.getMontoReembolsado(),
                entity.getObservacion(),
                entity.getFechaRegistro()
        );
    }

    public CompensacionVentaEntity mapearDominioAEntity(CompensacionVenta dominio){
        if(dominio == null) return null;
        return new CompensacionVentaEntity(
                dominio.id(),
                ventaMapper.mapearAEntity(dominio.venta()),
                dominio.costoInsumos(),
                dominio.comisionCocina(),
                dominio.comisionTransbank(),
                dominio.montoTotalRetenido(),
                dominio.montoReembolsado(),
                dominio.observacion(),
                dominio.fechaRegistro()
        );
    }
}

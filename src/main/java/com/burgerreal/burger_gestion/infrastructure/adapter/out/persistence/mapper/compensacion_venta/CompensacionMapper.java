package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.compensacion_venta;

import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.CompensacionVentaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.venta.VentaMapper;
import org.springframework.stereotype.Component;

@Component
public class CompensacionMapper {

    private final VentaMapper ventaMapper;

    public CompensacionMapper(VentaMapper ventaMapper) {
        this.ventaMapper = ventaMapper;
    }

    public CompensacionVenta mapearEntityADominio(CompensacionVentaEntity compensacionVentaEntity){
        if(compensacionVentaEntity == null) return null;
        return new CompensacionVenta(
                compensacionVentaEntity.getId(),
                ventaMapper.mapearADomain(compensacionVentaEntity.getVenta()),
                compensacionVentaEntity.getCostoInsumos(),
                compensacionVentaEntity.getComisionCocina(),
                compensacionVentaEntity.getComisionTransbank(),
                compensacionVentaEntity.getMontoTotalRetenido(),
                compensacionVentaEntity.getMontoReembolsado(),
                compensacionVentaEntity.getObservacion(),
                compensacionVentaEntity.getFechaRegistro()
        );
    }

    public CompensacionVentaEntity mapearDominioAEntity(CompensacionVenta compensacionVenta){
        if(compensacionVenta == null) return null;
        return new CompensacionVentaEntity(
                compensacionVenta.id(),
                ventaMapper.mapearAEntity(compensacionVenta.venta()),
                compensacionVenta.costoInsumos(),
                compensacionVenta.comisionCocina(),
                compensacionVenta.comisionTransbank(),
                compensacionVenta.montoTotalRetenido(),
                compensacionVenta.montoReembolsado(),
                compensacionVenta.observacion(),
                compensacionVenta.fechaRegistro()
        );
    }
}

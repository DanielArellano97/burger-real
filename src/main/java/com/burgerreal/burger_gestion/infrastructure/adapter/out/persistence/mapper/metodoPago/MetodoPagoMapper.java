package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.metodoPago;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoDetalleParaVentaResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoDetalleResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoResumenParaVentaResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoResumenResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MetodoPagoEntity;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagoMapper {

    // Métodos auxiliares para el Metodo de Pago
    public MetodoPagoEntity mapearAEntity(MetodoPago domain) {
        if (domain == null) return null;

        return new MetodoPagoEntity(
                domain.id(),
                domain.nombre(),
                domain.porcentajeComision(),
                domain.comisionFija(),
                domain.estaActivo()
        );
    }

    public MetodoPago mapearADominio(MetodoPagoEntity entity) {
        if (entity == null) return null;
        return new MetodoPago(
                entity.getId(),
                entity.getNombre(),
                entity.getPorcentajeComision(),
                entity.getComisionFija(),
                entity.isEstaActivo()
        );
    }

    public MetodoPagoDetalleResponse mapearADetalleResponse(MetodoPago dominio){
        if(dominio == null) return null;

        return new MetodoPagoDetalleResponse(
                dominio.id(),
                dominio.nombre(),
                dominio.porcentajeComision(),
                dominio.comisionFija(),
                dominio.estaActivo());
    }

    public MetodoPagoResumenResponse mapearAResumenResponse(MetodoPago dominio){
        if(dominio == null) return null;

        return new MetodoPagoResumenResponse(
                dominio.id(),
                dominio.nombre());
    }

    public MetodoPagoDetalleParaVentaResponse mapearADetalleParaVentaResponse(MetodoPago dominio){
        if(dominio == null) return null;

        return new MetodoPagoDetalleParaVentaResponse(
                dominio.id(),
                dominio.nombre(),
                dominio.porcentajeComision(),
                dominio.comisionFija());
    }

    public MetodoPagoResumenParaVentaResponse mapearAResumenParaVentaResponse(MetodoPago dominio){
        if(dominio == null) return null;

        return new MetodoPagoResumenParaVentaResponse(
                dominio.id(),
                dominio.nombre());
    }
}

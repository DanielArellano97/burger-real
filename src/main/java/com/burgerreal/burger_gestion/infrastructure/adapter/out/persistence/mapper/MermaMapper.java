package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.Merma;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.merma.MermaListResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MermaEntity;
import org.springframework.stereotype.Component;

@Component
public class MermaMapper {

    public MermaEntity toEntity(Merma dominio) {
        if (dominio == null) return null;
        return new MermaEntity(
                dominio.insumoId(),
                dominio.nombreInsumo(),
                dominio.cantidad(),
                dominio.motivo(),
                dominio.fecha(),
                dominio.costoPerdido()
        );
    }

    public Merma toDominio(MermaEntity entity) {
        if (entity == null) return null;
        return new Merma(
                entity.getId(),
                entity.getInsumoId(),
                entity.getNombreInsumo(),
                entity.getCantidad(),
                entity.getMotivo(),
                entity.getFecha(),
                entity.getCostoPerdido()
        );
    }

    public MermaListResponse toListResponse(Merma dominio){
        if (dominio == null) return null;

        return new MermaListResponse(
                dominio.id(),
                dominio.insumoId(),
                dominio.nombreInsumo(), // Para histórico, por si el insumo se borra
                dominio.cantidad(),
                dominio.motivo(),
                dominio.fecha(),
                dominio.costoPerdido()
        );
    }
}

package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.CrearInsumoRequest;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.CrearInsumoResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.ResponseResumenInsumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.InsumoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InsumoMapper {

    public Insumo toDominio(InsumoEntity entity) {
        if (entity == null) return null;
        return new Insumo(
                entity.getId(),
                entity.getNombre(),
                entity.getCostoUnitario(),
                entity.getStockActual(),
                entity.getStockMinimo(),
                entity.getUnidadMedida(),
                entity.getCategoria(),
                entity.isEsInventariable(),
                entity.getReceta() != null ? entity.getReceta().getId() : null
        );
    }

    public InsumoEntity toEntity(Insumo dominio, RecetaEntity receta) {
        if (dominio == null) return null;
        return new InsumoEntity(
                dominio.nombre(),
                dominio.costoUnitario(),
                dominio.stockActual(),
                dominio.stockMinimo(),
                dominio.unidadMedida(),
                dominio.categoria(),
                dominio.esInventariable(),
                receta
        );
    }

    public InsumoEntity toEntityStocks(Insumo dominio, RecetaEntity receta) {
        if (dominio == null) return null;
        return new InsumoEntity(
                dominio.id(),
                dominio.nombre(),
                dominio.costoUnitario(),
                dominio.stockActual(),
                dominio.stockMinimo(),
                dominio.unidadMedida(),
                dominio.categoria(),
                dominio.esInventariable(),
                receta
        );
    }

    public Insumo toDominio(CrearInsumoRequest crearInsumoRequest) {
        if (crearInsumoRequest == null) return null;

        return Insumo.nuevoInsumo(
                crearInsumoRequest.nombre(),
                BigDecimal.ZERO,
                crearInsumoRequest.stockActual(),
                crearInsumoRequest.stockMinimo(),
                crearInsumoRequest.unidadMedida(),
                crearInsumoRequest.categoria(),
                crearInsumoRequest.esInventariable(),
                crearInsumoRequest.idReceta()
        );
    }

    public CrearInsumoResponse toCrearResponse(Insumo dominio) {
        if (dominio == null) return null;
        return new CrearInsumoResponse(
                dominio.id(),
                dominio.nombre(),
                dominio.costoUnitario(),
                dominio.stockActual(),
                dominio.stockMinimo(),
                dominio.unidadMedida(),
                dominio.categoria()
        );
    }

    public ResponseResumenInsumo toResponseResumenInsumo(Insumo dominio) {
        if (dominio == null) return null;
        return new ResponseResumenInsumo(
                dominio.id(),
                dominio.nombre(),
                dominio.costoUnitario(),
                dominio.stockActual(),
                dominio.unidadMedida(),
                dominio.categoria(),
                dominio.requiereReposicion()

        );
    }

    public InsumoEntity toEntityParaProducto(Long id) {
        if (id == null) return null;
        return new InsumoEntity(
                id
        );
    }
}

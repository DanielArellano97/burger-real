package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;
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
}

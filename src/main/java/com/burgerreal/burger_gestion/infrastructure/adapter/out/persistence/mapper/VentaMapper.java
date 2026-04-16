package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MetodoPagoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    private final MetodoPagoMapper metodoPagoMapper;

    public VentaMapper(MetodoPagoMapper metodoPagoMapper) {
        this.metodoPagoMapper = metodoPagoMapper;
    }

    /**
     * De Record (Dominio) a Entity (Base de Datos)
     */
    public VentaEntity mapearAEntity(Venta domain) {
        if (domain == null) return null;

        // Obtenemos el MetodoPagoEntity (podría ser null)
        MetodoPagoEntity metodoPagoEntity = (domain.metodoPago() != null)
                ? metodoPagoMapper.mapearAEntity(domain.metodoPago())
                : null;

        // Usamos una evaluación explícita para decidir qué constructor usar
        if (java.util.Objects.isNull(domain.id())) {
            // Caso: Venta Nueva (Sin ID)
            return new VentaEntity(
                    domain.montoTotalBruto(),
                    domain.costoTotalInsumos(),
                    domain.comisionPasarela(),
                    domain.gananciaNeta(),
                    metodoPagoEntity
            );
        } else {
            // Caso: Venta Existente (Con ID y Fecha)
            return new VentaEntity(
                    domain.id(),
                    domain.fecha(),
                    domain.montoTotalBruto(),
                    domain.costoTotalInsumos(),
                    domain.comisionPasarela(),
                    domain.gananciaNeta(),
                    metodoPagoEntity
            );
        }
    }

    /**
     * De Entity (Base de Datos) a Record (Dominio)
     */
    public Venta mapearADomain(VentaEntity entity) {
        if (entity == null) return null;

        return new Venta(
                entity.getId(),
                entity.getFecha(),
                entity.getMontoTotalBruto(),
                entity.getCostoTotalInsumos(),
                entity.getComisionPasarela(),
                entity.getGananciaNeta(),
                metodoPagoMapper.mapearADominio(entity.getMetodoPago())
        );
    }
}

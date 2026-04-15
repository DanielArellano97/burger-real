package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.domain.model.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    /**
     * De Record (Dominio) a Entity (Base de Datos)
     */
    public VentaEntity mapearAEntity(Venta domain) {
        if (domain == null) return null;

        // Obtenemos el MetodoPagoEntity (podría ser null)
        MetodoPagoEntity metodoPagoEntity = (domain.metodoPago() != null)
                ? toMetodoPagoEntity(domain.metodoPago())
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
                toMetodoPagoDomain(entity.getMetodoPago())
        );
    }

    // Métodos auxiliares para el Metodo de Pago
    private MetodoPagoEntity toMetodoPagoEntity(MetodoPago domain) {
        MetodoPagoEntity entity = new MetodoPagoEntity();
        entity.setId(domain.id());
        entity.setNombre(domain.nombre());
        entity.setPorcentajeComision(domain.porcentajeComision());
        entity.setComisionFija(domain.comisionFija());
        entity.setEstaActivo(domain.estaActivo());
        return entity;
    }

    private MetodoPago toMetodoPagoDomain(MetodoPagoEntity entity) {
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

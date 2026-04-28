package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.venta;

import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.VentaDetalleResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.VentaResumenResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MetodoPagoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.metodoPago.MetodoPagoMapper;
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

        // Si no viene con id significa que es una insercion
        if (domain.id() == null) {
            // Caso: Venta Nueva (Sin ID)
            return new VentaEntity(
                    domain.fechaInicioCocina(),
                    domain.fechaEntregaCliente(),
                    domain.montoTotalBruto(),
                    domain.costoTotalInsumos(),
                    domain.comisionPasarela(),
                    domain.gananciaNeta(),
                    domain.pagoConfirmado(),
                    domain.estado(),
                    metodoPagoEntity

            );
        } else {
            // Caso: Venta Existente (Con ID y Fecha)
            return new VentaEntity(
                    domain.id(),
                    domain.fecha(),
                    domain.fechaInicioCocina(),
                    domain.fechaEntregaCliente(),
                    domain.montoTotalBruto(),
                    domain.costoTotalInsumos(),
                    domain.comisionPasarela(),
                    domain.gananciaNeta(),
                    domain.pagoConfirmado(),
                    domain.cargoPorAnulacionCocina(),
                    domain.estado(),
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
                entity.getFechaInicioCocina(),
                entity.getFechaEntregaCliente(),
                entity.getMontoTotalBruto(),
                entity.getCostoTotalInsumos(),
                entity.getComisionPasarela(),
                entity.getGananciaNeta(),
                entity.isPagoConfirmado(),
                entity.getCargoPorAnulacionCocina(),
                entity.getEstado(),
                metodoPagoMapper.mapearADominio(entity.getMetodoPago())
        );
    }

    /**
     * De Dominio (Record) a Response Detalle
     */
    public VentaDetalleResponse mapearADetalleResponse(Venta dominio) {
        if (dominio == null) return null;

        return new VentaDetalleResponse(
                dominio.id(),
                dominio.fecha(),
                dominio.montoTotalBruto(),
                dominio.costoTotalInsumos(),
                dominio.comisionPasarela(),
                dominio.gananciaNeta(),
                dominio.pagoConfirmado(),
                dominio.cargoPorAnulacionCocina(),
                dominio.estado(),
                metodoPagoMapper.mapearADetalleParaVentaResponse(dominio.metodoPago())
        );
    }

    /**
     * De Dominio (Record) a Response Resumen
     */
    public VentaResumenResponse mapearAResumenResponse(Venta dominio) {
        if (dominio == null) return null;

        return new VentaResumenResponse(
                dominio.id(),
                dominio.fecha(),
                dominio.montoTotalBruto(),
                dominio.gananciaNeta(),
                dominio.estado(),
                metodoPagoMapper.mapearAResumenParaVentaResponse(dominio.metodoPago())
        );
    }
}

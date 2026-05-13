package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.application.port.in.venta.RegistrarVentaUseCase.Command;
import com.burgerreal.burger_gestion.application.port.in.venta.RegistrarVentaUseCase.ItemVenta;
import com.burgerreal.burger_gestion.domain.model.ReporteVentas;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.CrearVentaRequest;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.ReporteVentasResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.VentaDetalleResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.VentaResumenResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MetodoPagoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaMapper {

    private final MetodoPagoMapper metodoPagoMapper;

    public VentaMapper(MetodoPagoMapper metodoPagoMapper) {
        this.metodoPagoMapper = metodoPagoMapper;
    }

    /**
     * De Record (Dominio) a Entity (Base de Datos)
     */
    public VentaEntity mapearAEntity(Venta dominio) {
        if (dominio == null) return null;

        // Obtenemos el MetodoPagoEntity (podría ser null)
        MetodoPagoEntity metodoPagoEntity = (dominio.metodoPago() != null)
                ? metodoPagoMapper.mapearAEntity(dominio.metodoPago())
                : null;

        // Si no viene con id significa que es una insercion
        if (dominio.id() == null) {
            // Caso: Venta Nueva (Sin ID)
            return new VentaEntity(
                    dominio.fechaInicioCocina(),
                    dominio.fechaEntregaCliente(),
                    dominio.montoTotalBruto(),
                    dominio.costoTotalInsumos(),
                    dominio.comisionPasarela(),
                    dominio.gananciaNeta(),
                    dominio.pagoConfirmado(),
                    dominio.estado(),
                    metodoPagoEntity

            );
        } else {
            // Caso: Venta Existente (Con ID y Fecha)
            return new VentaEntity(
                    dominio.id(),
                    dominio.fecha(),
                    dominio.fechaInicioCocina(),
                    dominio.fechaEntregaCliente(),
                    dominio.montoTotalBruto(),
                    dominio.costoTotalInsumos(),
                    dominio.comisionPasarela(),
                    dominio.gananciaNeta(),
                    dominio.pagoConfirmado(),
                    dominio.cargoPorAnulacionCocina(),
                    dominio.estado(),
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

    /**
     * De Dominio (Record) a Response Reporte Venta
     */
    public ReporteVentasResponse mapearAReporteVentaResponse(ReporteVentas dominio) {
        if (dominio == null) return null;

        return new ReporteVentasResponse(
            dominio.ingresosVentasBruto(),
            dominio.recuperacionInsumosAnulados(),
            dominio.ingresosMultasCocina(),
            dominio.costoInsumosVendidos(),
            dominio.costoInsumosPerdidos(),
            dominio.comisionesBancariasTotales(),
            dominio.balanceRealNeto(),
            dominio.cantidadVentasCompletadas(),
            dominio.cantidadVentasAnuladas(),
            dominio.tiempoPromedioCocinaMinutos(),
            dominio.tiempoPromedioEsperaTotalMinutos()
        );
    }

    public Command mapearACommand(CrearVentaRequest request) {
        if (request == null) return null;

        List<ItemVenta> items = request.items().stream()
                .map(item -> new ItemVenta(
                        item.productoId(),
                        item.cantidad()
                ))
                .toList();

        return new Command(
                items,
                request.pagoConfirmado(),
                request.metodoPagoId()
        );
    }
}

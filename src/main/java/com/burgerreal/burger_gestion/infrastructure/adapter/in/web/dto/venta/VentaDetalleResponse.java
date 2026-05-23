package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.item_venta.ResponseItemVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoDetalleParaVentaResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VentaDetalleResponse(
        Long id,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime fecha,
        BigDecimal montoTotalBruto,
        BigDecimal costoTotalInsumos,
        BigDecimal comisionPasarela,
        BigDecimal gananciaNeta,
        boolean pagoConfirmado,
        Long cargoPorAnulacionCocina,
        EstadoVenta estadoVenta,
        MetodoPagoDetalleParaVentaResponse metodoPago,
        List<ResponseItemVenta> items
) {
}

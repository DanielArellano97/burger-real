package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.metodo_pago.MetodoPagoDetalleParaVentaResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record VentaDetalleResponse(
        Long id,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime fecha,
        Long montoTotalBruto,
        Long costoTotalInsumos,
        Long comisionPasarela,
        Long gananciaNeta,
        boolean pagoConfirmado,
        Long cargoPorAnulacionCocina,
        EstadoVenta estadoVenta,
        MetodoPagoDetalleParaVentaResponse metodoPago
) {

}

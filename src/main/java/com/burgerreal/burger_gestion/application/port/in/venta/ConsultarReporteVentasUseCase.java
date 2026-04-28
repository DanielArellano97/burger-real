package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.ReporteVentasResponse;

import java.time.LocalDate;

public interface ConsultarReporteVentasUseCase {

    ReporteVentasResponse ejecutar(LocalDate inicio, LocalDate fin);
}

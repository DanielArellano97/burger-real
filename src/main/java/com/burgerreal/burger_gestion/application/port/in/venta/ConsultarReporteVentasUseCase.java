package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.domain.model.ReporteVentas;

import java.time.LocalDate;

public interface ConsultarReporteVentasUseCase {

    ReporteVentas ejecutar(LocalDate inicio, LocalDate fin);
}

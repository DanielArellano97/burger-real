package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ListarVentasUseCase {

    Page<Venta> ejecutar(LocalDate inicio, LocalDate fin, EstadoVenta estadoVenta, Pageable pageable);
}

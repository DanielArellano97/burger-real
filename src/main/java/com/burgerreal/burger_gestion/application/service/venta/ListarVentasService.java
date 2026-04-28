package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.ListarVentasUseCase;
import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ListarVentasService implements ListarVentasUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;

    public ListarVentasService(VentaRepositoryPort ventaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Venta> ejecutar(LocalDate inicio, LocalDate fin, EstadoVenta estado, Pageable pageable) {
        LocalDateTime fechaInicio = (inicio != null) ? inicio.atStartOfDay() : null;
        LocalDateTime fechaFin = (fin != null) ? fin.atTime(LocalTime.MAX) : null;
        return ventaRepositoryPort.listarConFiltrosOpcionales(fechaInicio, fechaFin, estado, pageable);
    }
}

package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.ConsultarReporteVentasUseCase;
import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.ReporteVentasResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ConsultarReporteVentasService implements ConsultarReporteVentasUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;

    public ConsultarReporteVentasService(VentaRepositoryPort ventaRepositoryPort) {
        this.ventaRepositoryPort = ventaRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteVentasResponse ejecutar(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = (inicio != null) ? inicio.atStartOfDay() : LocalDate.now().atStartOfDay();
        LocalDateTime finDT = (fin != null) ? fin.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);

        List<Venta> ventas = ventaRepositoryPort.buscarVentasPorRangoFecha(inicioDT, finDT);

        long ingresosBrutos = 0, insumosVendidos = 0, insumosPerdidos = 0;
        long comisionesBancariasTotales = 0, recuperacionInsumosAnulados = 0, ingresosMultasCocina = 0;
        int completadas = 0, anuladas = 0, conteoCocina = 0, conteoEspera = 0;
        long totalMinutosCocina = 0, totalMinutosEsperaTotal = 0;

        for (Venta v : ventas) {
            ingresosMultasCocina += v.cargoPorAnulacionCocina();

            if (v.estado() == EstadoVenta.COMPLETADA) {
                completadas++;
                ingresosBrutos += v.montoTotalBruto();
                insumosVendidos += v.costoTotalInsumos();
                // ... (acumulación de tiempos) ...
            } else if (v.estado() == EstadoVenta.ANULADA) {
                anuladas++;
                if (v.cargoPorAnulacionCocina() > 0) {
                    recuperacionInsumosAnulados += v.costoTotalInsumos();
                    insumosPerdidos += v.costoTotalInsumos();
                }
            }
            comisionesBancariasTotales += v.comisionPasarela();
        }

        // Delegamos la creación y el cálculo del balance al Record
        return ReporteVentasResponse.of(
                ingresosBrutos, recuperacionInsumosAnulados, ingresosMultasCocina,
                insumosVendidos, insumosPerdidos, comisionesBancariasTotales,
                completadas, anuladas, totalMinutosCocina, conteoCocina,
                totalMinutosEsperaTotal, conteoEspera
        );
    }
}

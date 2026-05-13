package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.ConsultarReporteVentasUseCase;
import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.ReporteVentas;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public ReporteVentas ejecutar(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = (inicio != null) ? inicio.atStartOfDay() : LocalDate.now().atStartOfDay();
        LocalDateTime finDT = (fin != null) ? fin.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);

        List<Venta> ventas = ventaRepositoryPort.buscarVentasPorRangoFecha(inicioDT, finDT);

        BigDecimal ingresosBrutos = BigDecimal.ZERO;
        BigDecimal insumosVendidos = BigDecimal.ZERO;
        BigDecimal insumosPerdidos = BigDecimal.ZERO;
        BigDecimal comisionesBancariasTotales = BigDecimal.ZERO;
        BigDecimal recuperacionInsumosAnulados = BigDecimal.ZERO;

        // El cargo por anulación sigue siendo Long en el record Venta, lo trataremos con cuidado
        BigDecimal ingresosMultasCocina = BigDecimal.ZERO;

        int completadas = 0, anuladas = 0, conteoCocina = 0, conteoEspera = 0;
        long totalMinutosCocina = 0, totalMinutosEsperaTotal = 0;

        for (Venta v : ventas) {
            ingresosMultasCocina = ingresosMultasCocina.add(BigDecimal.valueOf(v.cargoPorAnulacionCocina()));

            if (v.estado() == EstadoVenta.COMPLETADA) {
                completadas++;
                ingresosBrutos = ingresosBrutos.add(v.montoTotalBruto());
                insumosVendidos = insumosVendidos.add(v.costoTotalInsumos());
                // ... (acumulación de tiempos) ...
            } else if (v.estado() == EstadoVenta.ANULADA) {
                anuladas++;
                if (v.cargoPorAnulacionCocina() > 0) {
                    recuperacionInsumosAnulados = recuperacionInsumosAnulados.add(v.costoTotalInsumos());
                    insumosPerdidos = insumosPerdidos.add(v.costoTotalInsumos());
                }
            }
            comisionesBancariasTotales = comisionesBancariasTotales.add(v.comisionPasarela());        }

        // Delegamos la creación y el cálculo del balance al Record
        return ReporteVentas.crearReporteVenta(
                ingresosBrutos, recuperacionInsumosAnulados, ingresosMultasCocina,
                insumosVendidos, insumosPerdidos, comisionesBancariasTotales,
                completadas, anuladas, totalMinutosCocina, conteoCocina,
                totalMinutosEsperaTotal, conteoEspera
        );
    }
}

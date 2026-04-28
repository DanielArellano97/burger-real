package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VentaRepositoryPort {

    Venta guardar(Venta venta);
    Page<Venta> listarConFiltrosOpcionales(LocalDateTime inicio, LocalDateTime fin, EstadoVenta estado, Pageable pageable);
    Optional<Venta> buscarPorId(Long id);
    void eliminarPorId(Long ventaId);
    List<Venta> buscarVentasPorRangoFecha(LocalDateTime inicio, LocalDateTime fin);
}

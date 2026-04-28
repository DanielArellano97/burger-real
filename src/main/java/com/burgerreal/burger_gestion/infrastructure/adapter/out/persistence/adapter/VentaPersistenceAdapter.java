package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.venta.VentaMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaVentaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class VentaPersistenceAdapter implements VentaRepositoryPort {

    private final JpaVentaRepository jpaVentaRepository;
    private final VentaMapper ventaMapper;

    public VentaPersistenceAdapter(JpaVentaRepository jpaVentaRepository, VentaMapper ventaMapper) {
        this.jpaVentaRepository = jpaVentaRepository;
        this.ventaMapper = ventaMapper;
    }

    @Override
    public Venta guardar(Venta venta) {
        VentaEntity entityParaGuardar = ventaMapper.mapearAEntity(venta);
        VentaEntity entityGuardada= jpaVentaRepository.save(entityParaGuardar);
        return ventaMapper.mapearADomain(entityGuardada);
    }

    @Override
    public Page<Venta> listarConFiltrosOpcionales(LocalDateTime inicio, LocalDateTime fin, EstadoVenta estadoVenta, Pageable pageable) {
        return jpaVentaRepository.filtrarVentas(estadoVenta, inicio, fin, pageable).map(ventaMapper::mapearADomain);
    }

    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return jpaVentaRepository.findById(id).map(ventaMapper::mapearADomain);
    }

    @Override
    public void eliminarPorId(Long ventaId) {
        jpaVentaRepository.deleteById(ventaId);
    }

    @Override
    public List<Venta> buscarVentasPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return jpaVentaRepository.findByFechaBetween(inicio, fin).stream().map(ventaMapper::mapearADomain).toList();
    }


}

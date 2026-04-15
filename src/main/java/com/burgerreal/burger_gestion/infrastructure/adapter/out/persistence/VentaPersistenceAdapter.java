package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence;

import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
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
    public List<Venta> obtenerTodas() {
        return jpaVentaRepository.findAll().stream().map(ventaMapper::mapearADomain).toList();
    }
}

package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.CompensacionVenta;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.CompensacionVentaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.compensacion_venta.CompensacionMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaCompensacionVentaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompensacionVentaPersistenceAdapter implements CompensacionVentaRepositoryPort {

    private final JpaCompensacionVentaRepository jpaCompensacionVentaRepository;
    private final CompensacionMapper compensacionMapper;

    public CompensacionVentaPersistenceAdapter(JpaCompensacionVentaRepository jpaCompensacionVentaRepository, CompensacionMapper compensacionMapper) {
        this.jpaCompensacionVentaRepository = jpaCompensacionVentaRepository;
        this.compensacionMapper = compensacionMapper;
    }

    @Override
    public CompensacionVenta guardar(CompensacionVenta compensacionVenta) {
        CompensacionVentaEntity compensacionVentaGuardada = jpaCompensacionVentaRepository.save(compensacionMapper.mapearDominioAEntity(compensacionVenta));
        return compensacionMapper.mapearEntityADominio(compensacionVentaGuardada);
    }

    @Override
    public Optional<CompensacionVenta> buscarCompensacionVentaPorVentaId(Long id) {
        return jpaCompensacionVentaRepository.findByVentaId(id).map(compensacionMapper::mapearEntityADominio);
    }
}

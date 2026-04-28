package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.CompensacionVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCompensacionVentaRepository extends JpaRepository<CompensacionVentaEntity, Long> {

    // Spring entiende que debe buscar por el campo "venta" y su "id"
    Optional<CompensacionVentaEntity> findByVentaId(Long ventaId);
}

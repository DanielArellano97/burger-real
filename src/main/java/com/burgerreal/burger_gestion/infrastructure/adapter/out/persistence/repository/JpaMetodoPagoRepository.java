package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MetodoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMetodoPagoRepository extends JpaRepository<MetodoPagoEntity, Long> {

    List<MetodoPagoEntity> findByEstaActivoTrue();
}

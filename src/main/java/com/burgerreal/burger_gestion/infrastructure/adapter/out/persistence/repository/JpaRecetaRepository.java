package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRecetaRepository extends JpaRepository<RecetaEntity, Long> {
}

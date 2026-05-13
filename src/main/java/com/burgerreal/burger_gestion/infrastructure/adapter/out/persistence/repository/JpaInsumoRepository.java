package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.InsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInsumoRepository extends JpaRepository<InsumoEntity, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}

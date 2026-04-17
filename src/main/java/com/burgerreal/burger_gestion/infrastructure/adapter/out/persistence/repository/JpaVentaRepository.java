package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaVentaRepository extends JpaRepository<VentaEntity, Long> {
    // Aquí Spring Boot hace toda la magia de los SELECT, INSERT y DELETE
}

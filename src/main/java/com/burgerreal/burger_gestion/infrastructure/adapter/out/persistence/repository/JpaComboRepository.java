package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ComboEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaComboRepository extends JpaRepository<ComboEntity, Long> {

    // Trae solo los combos que están activos para la venta
    List<ComboEntity> findByDisponibleTrue();
}
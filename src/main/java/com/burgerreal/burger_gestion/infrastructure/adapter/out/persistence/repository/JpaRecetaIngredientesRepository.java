package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaIngredientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRecetaIngredientesRepository extends JpaRepository<RecetaIngredientesEntity, Long> {
    // Esto te servirá si alguna vez quieres ver todos los "extras" de una receta específica
    List<RecetaIngredientesEntity> findByRecetaId(Long recetaId);
}

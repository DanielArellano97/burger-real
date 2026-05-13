package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoMercadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaParametroMercadoRepository extends JpaRepository<ProductoMercadoEntity, Long> {

    Optional<ProductoMercadoEntity> findByNombre(String nombre);
}

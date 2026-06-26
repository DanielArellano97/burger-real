package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long> {

    List<ProductoEntity> findAllByOrderByIdAsc();
}

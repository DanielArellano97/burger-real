package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MermaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMermaRepository extends JpaRepository<MermaEntity, Long> {
}

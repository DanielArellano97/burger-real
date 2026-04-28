package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaVentaRepository extends JpaRepository<VentaEntity, Long> {

    @Query("SELECT v FROM VentaEntity v WHERE " +
            "(cast(:estado as string) IS NULL OR v.estado = :estado) AND " +
            "(cast(:inicio as timestamp) IS NULL OR v.fecha >= :inicio) AND " +
            "(cast(:fin as timestamp) IS NULL OR v.fecha <= :fin)")
    Page<VentaEntity> filtrarVentas(
            @Param("estado") EstadoVenta estado,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            Pageable pageable
    );

    List<VentaEntity> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}

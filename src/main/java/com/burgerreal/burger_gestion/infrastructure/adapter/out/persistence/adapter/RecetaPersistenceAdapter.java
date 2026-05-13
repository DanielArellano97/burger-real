package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.port.out.RecetaRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.RecetaMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaRecetaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RecetaPersistenceAdapter implements RecetaRepositoryPort {

    private final JpaRecetaRepository jpaRecetaRepository;
    private final RecetaMapper recetaMapper;

    public RecetaPersistenceAdapter(JpaRecetaRepository jpaRecetaRepository, RecetaMapper recetaMapper) {
        this.jpaRecetaRepository = jpaRecetaRepository;
        this.recetaMapper = recetaMapper;
    }

    @Override
    public Receta registrar(Receta receta) {
        RecetaEntity recetaAGuardar = recetaMapper.toEntity(receta);
        RecetaEntity recetaGuardada = jpaRecetaRepository.save(recetaAGuardar);
        return  recetaMapper.toDominio(recetaGuardada);
    }

    @Override
    public List<Receta> listar() {
        return jpaRecetaRepository.findAll().stream().map(recetaMapper::toDominio).toList();
    }

    @Override
    public Optional<Receta> buscarPorId(Long id) {
        return jpaRecetaRepository.findById(id).map(recetaMapper::toDominio);
    }
}

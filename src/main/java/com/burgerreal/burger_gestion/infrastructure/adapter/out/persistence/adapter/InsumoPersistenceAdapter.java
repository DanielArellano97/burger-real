package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.InsumoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.InsumoMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaInsumoRepository;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaRecetaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InsumoPersistenceAdapter implements InsumoRepositoryPort {

    private final JpaInsumoRepository jpaInsumoRepository;
    private final JpaRecetaRepository jpaRecetaRepository;
    private final InsumoMapper insumoMapper;

    public InsumoPersistenceAdapter(JpaInsumoRepository jpaInsumoRepository, JpaRecetaRepository jpaRecetaRepository, InsumoMapper insumoMapper) {
        this.jpaInsumoRepository = jpaInsumoRepository;
        this.jpaRecetaRepository = jpaRecetaRepository;
        this.insumoMapper = insumoMapper;
    }

    @Override
    public Insumo guardar(Insumo insumo) {

        RecetaEntity recetaEntity = null;
        // Si el insumo viene con un ID de receta, lo buscamos en la DB
        if (insumo.recetaId() != null) {
            recetaEntity = jpaRecetaRepository.findById(insumo.recetaId())
                    .orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + insumo.recetaId()));
        }

        InsumoEntity entity = insumoMapper.toEntityStocks(insumo, recetaEntity);
        InsumoEntity guardado = jpaInsumoRepository.save(entity);
        return insumoMapper.toDominio(jpaInsumoRepository.save(guardado));
    }

    @Override
    public Optional<Insumo> buscarPorId(Long id) {
        return jpaInsumoRepository.findById(id).map(insumoMapper::toDominio);
    }

    @Override
    public List<Insumo> listarTodos() {
        return jpaInsumoRepository.findAll().stream()
                .map(insumoMapper::toDominio).toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaInsumoRepository.deleteById(id);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return jpaInsumoRepository.existsByNombreIgnoreCase(nombre);
    }
}

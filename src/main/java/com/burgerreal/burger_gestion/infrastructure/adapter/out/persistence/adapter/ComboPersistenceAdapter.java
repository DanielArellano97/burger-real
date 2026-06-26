package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Combo;
import com.burgerreal.burger_gestion.domain.port.out.ComboRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ComboEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ComboMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaComboRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComboPersistenceAdapter implements ComboRepositoryPort {

    private final JpaComboRepository jpaComboRepository;
    private final ComboMapper comboMapper;

    public ComboPersistenceAdapter(JpaComboRepository jpaComboRepository, ComboMapper comboMapper) {
        this.jpaComboRepository = jpaComboRepository;
        this.comboMapper = comboMapper;
    }

    @Override
    public List<Combo> listarTodos() {
        List<ComboEntity> entidades = jpaComboRepository.findByDisponibleTrue();
        return entidades.stream()
                .map(comboMapper::toDomain)
                .collect(Collectors.toList());
    }
}

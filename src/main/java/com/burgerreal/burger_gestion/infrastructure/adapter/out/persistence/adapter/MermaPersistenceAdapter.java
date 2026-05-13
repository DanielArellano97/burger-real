package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Merma;
import com.burgerreal.burger_gestion.domain.port.out.MermaRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.MermaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.MermaMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaMermaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MermaPersistenceAdapter implements MermaRepositoryPort {

    private final JpaMermaRepository jpaMermaRepository;
    private final MermaMapper mermaMapper;

    public MermaPersistenceAdapter(JpaMermaRepository jpaMermaRepository, MermaMapper mermaMapper) {
        this.jpaMermaRepository = jpaMermaRepository;
        this.mermaMapper = mermaMapper;
    }

    @Override
    @Transactional
    public Merma guardar(Merma merma) {
        MermaEntity entity = mermaMapper.toEntity(merma);
        return mermaMapper.toDominio(jpaMermaRepository.save(entity));
    }

    @Override
    public List<Merma> buscarTodas() {
        return jpaMermaRepository.findAll().stream()
                .map(mermaMapper::toDominio)
                .toList();
    }
}

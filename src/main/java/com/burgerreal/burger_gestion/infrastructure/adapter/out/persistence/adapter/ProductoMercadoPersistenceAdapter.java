package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoMercadoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ProductoMercadoMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaParametroMercadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductoMercadoPersistenceAdapter implements ProductoMercadoRepositoryPort {

    private final JpaParametroMercadoRepository jpaParametroMercadoRepository;
    private final ProductoMercadoMapper parametroMercadoMapper;

    public ProductoMercadoPersistenceAdapter(JpaParametroMercadoRepository jpaParametroMercadoRepository, ProductoMercadoMapper parametroMercadoMapper) {
        this.jpaParametroMercadoRepository = jpaParametroMercadoRepository;
        this.parametroMercadoMapper = parametroMercadoMapper;
    }

    @Override
    public ProductoMercado guardar(ProductoMercado parametroMercado) {
        ProductoMercadoEntity entityAGuardar = parametroMercadoMapper.toEntity(parametroMercado);
        ProductoMercadoEntity entityGuardada= jpaParametroMercadoRepository.save(entityAGuardar);
        return parametroMercadoMapper.toDominio(entityGuardada);
    }

    @Override
    public List<ProductoMercado> listar() {
        return jpaParametroMercadoRepository.findAll().stream().map(parametroMercadoMapper::toDominio).toList();
    }

    @Override
    public Optional<ProductoMercado> obtenerPorId(Long id) {
        return jpaParametroMercadoRepository.findById(id).map(parametroMercadoMapper::toDominio);
    }

    @Override
    public Optional<ProductoMercado> buscarPorNombre(String nombreParametroMercado) {
        return jpaParametroMercadoRepository.findByNombre(nombreParametroMercado).map(parametroMercadoMapper::toDominio);
    }
}

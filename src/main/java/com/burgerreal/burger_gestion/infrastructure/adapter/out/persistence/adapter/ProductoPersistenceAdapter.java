package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.port.out.ProductoRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaProductoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductoPersistenceAdapter implements ProductoRepositoryPort {

    private final JpaProductoRepository jpaProductoRepository;
    private final ProductoMapper productoMapper;

    public ProductoPersistenceAdapter(JpaProductoRepository jpaProductoRepository, ProductoMapper productoMapper) {
        this.jpaProductoRepository = jpaProductoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoEntity productoAGuardar = productoMapper.toEntity(producto);
        ProductoEntity productoGuardado = jpaProductoRepository.save(productoAGuardar);
        return productoMapper.toDominio(productoGuardado);
    }

    @Override
    public List<Producto> listarTodos() {
        List<ProductoEntity> productosEncontrados = jpaProductoRepository.findAll();
        return productoMapper.toDominioList(productosEncontrados);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id){
        return jpaProductoRepository.findById(id).map(productoMapper::toDominio);

    }

    @Override
    public void eliminar(Long id) {
        jpaProductoRepository.deleteById(id);
    }
}

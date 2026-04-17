package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.MetodoPago;
import com.burgerreal.burger_gestion.domain.port.out.MetodoPagoRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.MetodoPagoMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaMetodoPagoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MetodoPagoPersistenceAdapter implements MetodoPagoRepositoryPort {

    private final JpaMetodoPagoRepository jpaMetodoPagoRepository;
    private final MetodoPagoMapper metodoPagoMapper;

    public MetodoPagoPersistenceAdapter(JpaMetodoPagoRepository jpaMetodoPagoRepository, MetodoPagoMapper metodoPagoMapper) {
        this.jpaMetodoPagoRepository = jpaMetodoPagoRepository;
        this.metodoPagoMapper = metodoPagoMapper;
    }

    @Override
    public Optional<MetodoPago> buscarPorId(Long id) {
        return jpaMetodoPagoRepository.findById(id).map(metodoPagoMapper::mapearADominio);
    }

    @Override
    public List<MetodoPago> obtenerTodosLosActivos() {
        // Usamos el metodo personalizado que creamos en el JpaRepository
        return jpaMetodoPagoRepository.findByEstaActivoTrue()
                .stream()
                .map(metodoPagoMapper::mapearADominio)
                .toList(); // Usando el toList() moderno que acordamos antes
    }
}

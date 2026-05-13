package com.burgerreal.burger_gestion.application.service.producto;

import com.burgerreal.burger_gestion.application.port.in.producto.BuscarProductoPorIdUseCase;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.port.out.ProductoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class BuscarProductoPorIdService implements BuscarProductoPorIdUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    public BuscarProductoPorIdService(ProductoRepositoryPort productoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto ejecutar(Long id) {
        return productoRepositoryPort.buscarPorId(id).
                orElseThrow(() -> new EntityNotFoundException("El producto con ID: "+ id + " no fue encontrado"));
    }
}

package com.burgerreal.burger_gestion.application.service.producto;

import com.burgerreal.burger_gestion.application.port.in.producto.ListarProductosUseCase;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.port.out.ProductoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ListarProductosService implements ListarProductosUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    public ListarProductosService(ProductoRepositoryPort productoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> ejecutar() {
        return productoRepositoryPort.listarTodos();
    }
}

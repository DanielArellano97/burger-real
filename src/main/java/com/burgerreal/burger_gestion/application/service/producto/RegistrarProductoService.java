package com.burgerreal.burger_gestion.application.service.producto;

import com.burgerreal.burger_gestion.application.port.in.producto.RegistrarProductoUseCase;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.model.ProductoInsumo;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.ProductoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RegistrarProductoService implements RegistrarProductoUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;

    public RegistrarProductoService(ProductoRepositoryPort productoRepositoryPort, InsumoRepositoryPort insumoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
    }

    @Override
    @Transactional
    public Producto ejecutar(Command command) {

        List<ProductoInsumo> ingredientes = command.ingredientes().stream()
                .map(ingrediente -> {
                    Insumo insumo = insumoRepositoryPort.buscarPorId(ingrediente.insumoId())
                            .orElseThrow(() -> new RuntimeException("Insumo con ID: " + ingrediente.insumoId() + " no encontrado"));

                    return ProductoInsumo.crearProductoInsumo(insumo, ingrediente.cantidad());
                }).toList();

        Producto nuevoProducto = Producto.crearProducto(command.nombre(), command.descripcion(), command.precioVenta(),
                command.imagenUrl(), true, ingredientes);

        return productoRepositoryPort.guardar(nuevoProducto);
    }
}

package com.burgerreal.burger_gestion.domain.port.out;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;

import java.util.List;
import java.util.Optional;

public interface ProductoMercadoRepositoryPort {
    ProductoMercado guardar(ProductoMercado parametroMercado);
    List<ProductoMercado> listar();
    Optional<ProductoMercado> obtenerPorId(Long id);
    Optional<ProductoMercado> buscarPorNombre(String nombreParametroMercado);
}

package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.producto.BuscarProductoPorIdUseCase;
import com.burgerreal.burger_gestion.application.port.in.producto.ListarProductosUseCase;
import com.burgerreal.burger_gestion.application.port.in.producto.RegistrarProductoUseCase;
import com.burgerreal.burger_gestion.application.port.in.producto.RegistrarProductoUseCase.Command;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final RegistrarProductoUseCase registrarProductoUseCase;
    private final ListarProductosUseCase listarProductosUseCase;
    private final BuscarProductoPorIdUseCase buscarProductoPorIdUseCase;
    private final ProductoMapper productoMapper;

    public ProductoController(RegistrarProductoUseCase registrarProductoUseCase, ListarProductosUseCase listarProductosUseCase, BuscarProductoPorIdUseCase buscarProductoPorIdUseCase, ProductoMapper productoMapper) {
        this.registrarProductoUseCase = registrarProductoUseCase;
        this.listarProductosUseCase = listarProductosUseCase;
        this.buscarProductoPorIdUseCase = buscarProductoPorIdUseCase;
        this.productoMapper = productoMapper;
    }

    @PostMapping()
    public ResponseEntity<Long> registrar(@RequestBody Command command){
        Producto producto = registrarProductoUseCase.ejecutar(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto.id());
    }

    @GetMapping()
    public ResponseEntity<List<ResponseResumenProducto>> listar() {
        List<Producto> productos = listarProductosUseCase.ejecutar();
        List<ResponseResumenProducto> productosEncontrados = productoMapper.toResponseResumenProducto(productos);
        return ResponseEntity.ok(productosEncontrados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProducto> buscarPorId(@PathVariable Long id) {
        Producto producto = buscarProductoPorIdUseCase.ejecutar(id);
        ResponseProducto productoEncontrado = productoMapper.toResponseProducto(producto);
        return ResponseEntity.ok(productoEncontrado);
    }
}

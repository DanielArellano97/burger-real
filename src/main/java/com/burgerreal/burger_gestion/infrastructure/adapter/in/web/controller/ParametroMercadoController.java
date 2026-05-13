package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.BuscarProductoMercadoPorIdUseCase;
import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.ListarParametrosMercadoUseCase;
import com.burgerreal.burger_gestion.application.port.in.parametro_mercado.RegistrarProductoMercadoUseCase;
import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado.RequestParametroMercado;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto_mercado.ResponseParametroMercadoDetalle;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ProductoMercadoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametros-mercado")
public class ParametroMercadoController {

    private final RegistrarProductoMercadoUseCase registrarParametroMercadoUseCase;
    private final ListarParametrosMercadoUseCase listarParametrosMercadoUseCase;
    private final BuscarProductoMercadoPorIdUseCase buscarParametroMercadoPorIdUseCase;
    private final ProductoMercadoMapper productoMercadoMapper;

    public ParametroMercadoController(RegistrarProductoMercadoUseCase registrarParametroMercadoUseCase, ListarParametrosMercadoUseCase listarParametrosMercadoUseCase, BuscarProductoMercadoPorIdUseCase buscarParametroMercadoPorIdUseCase, ProductoMercadoMapper parametroMercadoMapper) {
        this.registrarParametroMercadoUseCase = registrarParametroMercadoUseCase;
        this.listarParametrosMercadoUseCase = listarParametrosMercadoUseCase;
        this.buscarParametroMercadoPorIdUseCase = buscarParametroMercadoPorIdUseCase;
        this.productoMercadoMapper = parametroMercadoMapper;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseParametroMercadoDetalle> registrar(@RequestBody RequestParametroMercado requestParametroMercado){
        ProductoMercado parametroMercadoAGuardar = productoMercadoMapper.toDominio(requestParametroMercado);
        ProductoMercado parametroMercadoGuardado = registrarParametroMercadoUseCase.ejecutar(parametroMercadoAGuardar);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoMercadoMapper.toResponseDetalle(parametroMercadoGuardado));
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseParametroMercadoDetalle>> listar(){
        List<ResponseParametroMercadoDetalle> parametrosMercado = listarParametrosMercadoUseCase.ejecutar().stream().map(productoMercadoMapper::toResponseDetalle).toList();
        return ResponseEntity.ok(parametrosMercado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseParametroMercadoDetalle> buscarPorId(@PathVariable Long id){
        ProductoMercado parametroMercadoEncontrado = buscarParametroMercadoPorIdUseCase.ejecutar(id);
        return ResponseEntity.ok(productoMercadoMapper.toResponseDetalle(parametroMercadoEncontrado));
    }
}

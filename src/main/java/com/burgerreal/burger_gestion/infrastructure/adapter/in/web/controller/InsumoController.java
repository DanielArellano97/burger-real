package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.insumo.CrearInsumoUseCase;
import com.burgerreal.burger_gestion.application.port.in.insumo.ListarInsumosUseCase;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.CrearInsumoRequest;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.CrearInsumoResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.insumo.ResponseResumenInsumo;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.InsumoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    private final CrearInsumoUseCase crearInsumoUseCase;
    private final ListarInsumosUseCase listarInsumosUseCase;
    private final InsumoMapper insumoMapper;

    public InsumoController(CrearInsumoUseCase crearInsumoUseCase, ListarInsumosUseCase listarInsumosUseCase, InsumoMapper insumoMapper) {
        this.crearInsumoUseCase = crearInsumoUseCase;
        this.listarInsumosUseCase = listarInsumosUseCase;
        this.insumoMapper = insumoMapper;
    }

    @PostMapping("/")
    public ResponseEntity<CrearInsumoResponse> registarInsumo(@RequestBody CrearInsumoRequest crearInsumoRequest){
        Insumo nuevoInsumo = insumoMapper.toDominio(crearInsumoRequest);

        Insumo insumoCreado = crearInsumoUseCase.ejecutar(nuevoInsumo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(insumoMapper.toCrearResponse(insumoCreado));
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseResumenInsumo>> listarInsumos(){
        List<ResponseResumenInsumo> listaInsumosResponse = listarInsumosUseCase.ejecutar().stream().map(insumoMapper::toResponseResumenInsumo).toList();
        return ResponseEntity.ok(listaInsumosResponse);

    }
}

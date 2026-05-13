package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.receta.BuscarRecetaPorIdUseCase;
import com.burgerreal.burger_gestion.application.port.in.receta.ListarRecetasUseCase;
import com.burgerreal.burger_gestion.application.port.in.receta.RegistrarRecetaUseCase;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta.RequestReceta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta.ResponseRecetaDetalle;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.RecetaMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RegistrarRecetaUseCase registrarRecetaUseCase;
    private final ListarRecetasUseCase listarRecetasUseCase;
    private final BuscarRecetaPorIdUseCase buscarRecetaPorIdUseCase;
    private final RecetaMapper recetaMapper;

    public RecetaController(RegistrarRecetaUseCase registrarRecetaUseCase, ListarRecetasUseCase listarRecetasUseCase, BuscarRecetaPorIdUseCase buscarRecetaPorIdUseCase, RecetaMapper recetaMapper) {
        this.registrarRecetaUseCase = registrarRecetaUseCase;
        this.listarRecetasUseCase = listarRecetasUseCase;
        this.buscarRecetaPorIdUseCase = buscarRecetaPorIdUseCase;
        this.recetaMapper = recetaMapper;
    }

    @PostMapping("/")
    public ResponseEntity<Long> registrar(@RequestBody RequestReceta requestReceta){
        Receta recetaAGuardar = recetaMapper.toDominio(requestReceta);
        Receta recetaGuardada= registrarRecetaUseCase.ejecutar(recetaAGuardar);
        return ResponseEntity.status(HttpStatus.CREATED).body(recetaGuardada.id());
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseRecetaDetalle>> listar(){
        List<ResponseRecetaDetalle> responseRecetaDetalles = listarRecetasUseCase.ejecutar().stream().map(recetaMapper::toResponse).toList();
        return ResponseEntity.ok(responseRecetaDetalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRecetaDetalle> buscarPorId(@PathVariable Long id){
        Receta receta = buscarRecetaPorIdUseCase.ejecutar(id);
        return ResponseEntity.ok(recetaMapper.toResponse(receta));
    }


}

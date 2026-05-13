package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.merma.ListarMermasUseCase;
import com.burgerreal.burger_gestion.application.port.in.merma.RegistrarMermaUseCase;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.merma.MermaListResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.merma.RegistrarMermaRequest;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.MermaMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mermas")
public class MermaController {

    private final RegistrarMermaUseCase registrarMermaUseCase;
    private final ListarMermasUseCase listarMermasUseCase;
    private final MermaMapper mermaMapper;

    public MermaController(RegistrarMermaUseCase registrarMermaUseCase, ListarMermasUseCase listarMermasUseCase, MermaMapper mermaMapper) {
        this.registrarMermaUseCase = registrarMermaUseCase;
        this.listarMermasUseCase = listarMermasUseCase;
        this.mermaMapper = mermaMapper;
    }

    @PostMapping("/")
    public ResponseEntity<String> registrar(@RequestBody RegistrarMermaRequest request) {
        registrarMermaUseCase.ejecutar(new RegistrarMermaUseCase.Command(
                request.insumoId(),
                request.cantidad(),
                request.motivo()
        ));
        return ResponseEntity.ok("Merma registrada exitosamente y stock actualizado.");
    }

    @GetMapping("/")
    public ResponseEntity<List<MermaListResponse>> listarMermas(){
        List<MermaListResponse> mermas = listarMermasUseCase.ejecutar().stream().map(mermaMapper::toListResponse).toList();
        return ResponseEntity.ok(mermas);
    }
}
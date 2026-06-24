package com.burgerreal.burger_gestion.infrastructure.adapter.in.web.controller;

import com.burgerreal.burger_gestion.application.port.in.combo.ListarCombosUseCase;
import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import com.burgerreal.burger_gestion.domain.model.Combo;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.combo.ComboDetalleResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.combo.ComboResponse;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/combos")
public class ComboController {

    private final ListarCombosUseCase listarCombosUseCase;

    public ComboController(ListarCombosUseCase listarCombosUseCase) {
        this.listarCombosUseCase = listarCombosUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ComboResponse>> listar() {
        List<Combo> combos = listarCombosUseCase.ejecutar();

        List<ComboResponse> response = combos.stream().map(combo -> {
            List<ComboDetalleResponse> detallesResponse = combo.detalles().stream().map(detalle -> {

                boolean requiereSeleccion = (detalle.varianteFija() == null)
                        && detalle.producto().categoria() == CategoriaProducto.BEBESTIBLE;

                ResponseResumenProducto.VarianteResumenDTO varianteSeleccionada = null;
                List<ResponseResumenProducto.VarianteResumenDTO> opcionesDisponibles = List.of();

                if (requiereSeleccion) {
                    // Si no hay variante fija (ej: la bebida), exponemos todas sus variantes del catálogo
                    opcionesDisponibles = detalle.producto().variantes().stream()
                            .map(v -> new ResponseResumenProducto.VarianteResumenDTO(v.id(), v.nombre(),
                                    v.nombreCorto(), v.precioExtra()))
                            .collect(Collectors.toList());
                } else if (detalle.varianteFija() != null) {
                    // Si ya viene fija (ej: aritos x10)
                    varianteSeleccionada = new ResponseResumenProducto.VarianteResumenDTO(
                            detalle.varianteFija().id(),
                            detalle.varianteFija().nombre(),
                            detalle.varianteFija().nombreCorto(),
                            detalle.varianteFija().precioExtra()
                    );
                }

                return new ComboDetalleResponse(
                        detalle.producto().id(),
                        detalle.producto().nombre(),
                        requiereSeleccion,
                        varianteSeleccionada,
                        opcionesDisponibles
                );
            }).collect(Collectors.toList());

            return new ComboResponse(
                    combo.id(),
                    combo.nombre(),
                    combo.descripcion(),
                    combo.precioCombo(),
                    combo.disponible(),
                    detallesResponse
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}

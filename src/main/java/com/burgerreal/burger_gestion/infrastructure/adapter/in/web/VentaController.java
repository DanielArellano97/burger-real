package com.burgerreal.burger_gestion.infrastructure.adapter.in.web;

import com.burgerreal.burger_gestion.application.port.in.RegistrarVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.VentaRequest;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.VentaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ventas")
public class VentaController {

    private final RegistrarVentaUseCase registrarVentaUseCase;

    public VentaController(RegistrarVentaUseCase registrarVentaUseCase) {
        this.registrarVentaUseCase = registrarVentaUseCase;
    }

    @PostMapping()
    public ResponseEntity<VentaResponse> registrarVenta(@RequestBody VentaRequest ventaRequest){
        Venta ventaCreada = registrarVentaUseCase.ejecutar(
                ventaRequest.montoBruto(),
                ventaRequest.costoInsumos(),
                ventaRequest.metodoPagoId());

        VentaResponse ventaResponse = new VentaResponse(
                ventaCreada.id(),
                ventaCreada.fecha(),
                ventaCreada.montoTotalBruto());

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaResponse);

    }
}

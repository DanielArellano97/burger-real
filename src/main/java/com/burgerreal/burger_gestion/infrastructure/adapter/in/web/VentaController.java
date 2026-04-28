package com.burgerreal.burger_gestion.infrastructure.adapter.in.web;

import com.burgerreal.burger_gestion.application.port.in.venta.*;
import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.domain.model.Venta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.*;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.venta.VentaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/ventas")
public class VentaController {

    private final RegistrarVentaUseCase registrarVentaUseCase;
    private final ListarVentasUseCase listarVentasUseCase;
    private final BuscarVentaPorIdUseCase buscarVentaPorIdUseCase;
    private final EliminarVentaPorIdUseCase eliminarVentaPorIdUseCase;
    private final AnularVentaPorIdUseCase anularVentaPorIdUseCase;
    private final IniciarCocinaVentaUseCase iniciarCocinaVentaUseCase;
    private final CompletarVentaUseCase completarVentaUseCase;
    private final EditarVentaUseCase editarVentaUseCase;
    private final ConsultarReporteVentasUseCase consultarReporteVentasUseCase;
    private final VentaMapper ventaMapper;

    public VentaController(RegistrarVentaUseCase registrarVentaUseCase, ListarVentasUseCase listarVentasUseCase, BuscarVentaPorIdUseCase buscarVentaPorIdUseCase, EliminarVentaPorIdUseCase eliminarVentaPorIdUseCase, AnularVentaPorIdUseCase anularVentaPorIdUseCase, IniciarCocinaVentaUseCase iniciarCocinaVentaUseCase, CompletarVentaUseCase completarVentaUseCase, EditarVentaUseCase editarVentaUseCase, ConsultarReporteVentasUseCase consultarReporteVentasUseCase, VentaMapper ventaMapper) {
        this.registrarVentaUseCase = registrarVentaUseCase;
        this.listarVentasUseCase = listarVentasUseCase;
        this.buscarVentaPorIdUseCase = buscarVentaPorIdUseCase;
        this.eliminarVentaPorIdUseCase = eliminarVentaPorIdUseCase;
        this.anularVentaPorIdUseCase = anularVentaPorIdUseCase;
        this.iniciarCocinaVentaUseCase = iniciarCocinaVentaUseCase;
        this.completarVentaUseCase = completarVentaUseCase;
        this.editarVentaUseCase = editarVentaUseCase;
        this.consultarReporteVentasUseCase = consultarReporteVentasUseCase;
        this.ventaMapper = ventaMapper;
    }

    @PostMapping()
    public ResponseEntity<VentaDetalleResponse> registrarVenta(@RequestBody CrearVentaRequest ventaRequest){
        Venta ventaCreada = registrarVentaUseCase.ejecutar(ventaRequest);

        VentaDetalleResponse ventaDetalleResponse = ventaMapper.mapearADetalleResponse(ventaCreada);

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaDetalleResponse);

    }

    @GetMapping
    public ResponseEntity<Page<VentaResumenResponse>> listarVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(required = false) EstadoVenta estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());

        Page<Venta> ventas  = listarVentasUseCase.ejecutar(inicio, fin, estado, pageable);

        Page<VentaResumenResponse> response = ventas.map(ventaMapper::mapearAResumenResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idVenta}")
    public ResponseEntity<VentaDetalleResponse> obtenerVentaPorId(@PathVariable Long idVenta){
        Venta ventaEncontrada = buscarVentaPorIdUseCase.ejecutar(idVenta);
        return ResponseEntity.status(HttpStatus.OK).body(ventaMapper.mapearADetalleResponse(ventaEncontrada));
    }

    /*
    Este endpoint realiza la eliminacion directa de la bdd
    esto es para usar en caso de pruebas o venta erronea y no dejar rastro en la bdd de ella
     */
    @DeleteMapping("/{idVenta}/eliminar-venta")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long idVenta){
        eliminarVentaPorIdUseCase.ejecutar(idVenta);
        return ResponseEntity.status(HttpStatus.OK).body("Venta con ID: " + idVenta + " eliminada correctamente");
    }

    @PatchMapping("/{idVenta}/anular-venta")
    public ResponseEntity<Void> anularVenta(@PathVariable Long idVenta, @RequestBody AnularVentaRequest anularVentaRequest){
        anularVentaPorIdUseCase.ejecutar(idVenta, anularVentaRequest.motivo());
        return ResponseEntity.noContent().build();
        // 204 No Content es perfecto para indicar que la acción fue exitosa
    }

    @PatchMapping("/{idVenta}/iniciar-cocina-venta")
    public ResponseEntity<Void> iniciarCocinaVenta(@PathVariable Long idVenta){
        iniciarCocinaVentaUseCase.ejecutar(idVenta);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idVenta}/completar-venta")
    public ResponseEntity<Void> completarVenta(@PathVariable Long idVenta){
        completarVentaUseCase.ejecutar(idVenta);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idVenta}/editar-venta")
    public ResponseEntity<Void> editarVenta(@PathVariable Long idVenta, @RequestBody EditarVentaRequest editarVentaRequest){
        editarVentaUseCase.ejecutar(idVenta, editarVentaRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reporte-ventas")
    public ResponseEntity<ReporteVentasResponse> reporteVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        ReporteVentasResponse reporteVentasResponse = consultarReporteVentasUseCase.ejecutar(inicio,fin);
        return ResponseEntity.ok(reporteVentasResponse);
    }
}

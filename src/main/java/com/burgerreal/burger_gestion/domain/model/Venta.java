package com.burgerreal.burger_gestion.domain.model;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.venta.CrearVentaRequest;

import java.time.LocalDateTime;

public record Venta (
     Long id,
     LocalDateTime fecha,
     LocalDateTime fechaInicioCocina,
     LocalDateTime fechaEntregaCliente,
     Long montoTotalBruto,
     Long costoTotalInsumos,
     Long comisionPasarela,
     Long gananciaNeta,
     boolean pagoConfirmado,
     Long cargoPorAnulacionCocina,
     EstadoVenta estado,
     MetodoPago metodoPago
){
    //Nueva venta flujo creacion
    public static Venta nuevaVenta(Long comision, Long neto, CrearVentaRequest crearVentaRequest, MetodoPago metodoPago){
        return new Venta(
                null,
                null,
                null,
                null,
                crearVentaRequest.montoBruto(),
                crearVentaRequest.costoInsumos(),
                comision,
                neto,
                crearVentaRequest.pagoConfirmado(),
                0L,
                EstadoVenta.PENDIENTE,
                metodoPago
        );
    }

    public Venta anular() {
        if (this.estado() == EstadoVenta.ANULADA)
            throw new IllegalStateException("La venta ya está anulada");

        if (this.estado() == EstadoVenta.COMPLETADA)
            throw new IllegalStateException("No se puede anular una venta que ya ha sido completada.");

        // Lógica de multa: si ya se preparó, cobramos 1500, si no, 0.
        Long multaAnulacionCocina = this.burgerPreparada() ? 1500L : 0L;

        return new Venta(
                this.id,               // Copio mi ID
                this.fecha,            // Copio mi FECHA DE CREACIÓN
                this.fechaInicioCocina,// <--- ¡ESTA ES LA CLAVE! Mantengo mi tiempo de cocina
                this.fechaEntregaCliente,
                this.montoTotalBruto,
                this.costoTotalInsumos,
                this.comisionPasarela,
                this.gananciaNeta,
                this.pagoConfirmado,
                multaAnulacionCocina,
                EstadoVenta.ANULADA,   // Lo único que realmente cambia
                this.metodoPago
        );
    }

    // Metodo de consulta simple para reglas de negocio
    public long calcularPerdidaInsumosSiEstaPreparada() {
        return this.burgerPreparada() ? this.costoTotalInsumos() : 0;
    }

    public Venta iniciarPreparacionVenta(){
        if(this.estado() == EstadoVenta.ANULADA)
            throw new IllegalStateException("No se puede iniciar la preparacion: la venta está ANULADA.");

        if(this.burgerPreparada())
            throw new IllegalStateException("No se puede iniciar la preparacion: la burger ya fue hecha.");

        return new Venta(
                this.id(), this.fecha(), LocalDateTime.now(), this.fechaEntregaCliente(), this.montoTotalBruto(), this.costoTotalInsumos(), this.comisionPasarela(),
                this.gananciaNeta(), this.pagoConfirmado(), this.cargoPorAnulacionCocina(), this.estado(), this.metodoPago()
        );
    }

    public Venta completarVenta() {
        // Aquí cambiamos el Enum de PENDIENTE a COMPLETADA
        if(this.estado() == EstadoVenta.ANULADA) throw new IllegalStateException("No se puede completar: la venta está ANULADA.");
        if(this.estado() == EstadoVenta.COMPLETADA) throw new IllegalStateException("No se puede completar: la venta ya esta completada.");
        if(!this.burgerPreparada()) throw new IllegalStateException("No se puede completar: cocina aún no prepara la burger.");
        return new Venta(
                this.id(), this.fecha(), this.fechaInicioCocina(), LocalDateTime.now(), this.montoTotalBruto(), this.costoTotalInsumos(), this.comisionPasarela(),
                this.gananciaNeta(), this.pagoConfirmado(), this.cargoPorAnulacionCocina(), EstadoVenta.COMPLETADA, this.metodoPago()
        );
    }

    public Venta editarVenta(Long nuevoMontoBruto, Long nuevoCostoInsumos, Long nuevaComision, Long nuevaGananciaNeta, MetodoPago nuevoMetodoPago) {
        // REGLA DE NEGOCIO: No se edita si ya se empezó a cocinar o si no está pendiente
        if (this.burgerPreparada()) {
            throw new IllegalStateException("No se puede editar la venta: la preparación en cocina ya inició.");
        }
        if (this.estado() != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden editar ventas que estén en estado PENDIENTE.");
        }

        return new Venta(
                this.id(), this.fecha(), this.fechaInicioCocina(), this.fechaEntregaCliente(), nuevoMontoBruto, nuevoCostoInsumos,
                nuevaComision, nuevaGananciaNeta, this.pagoConfirmado(), this.cargoPorAnulacionCocina(), this.estado(), nuevoMetodoPago
        );
    }

    // Esto NO es una variable, es un comportamiento
    public boolean burgerPreparada() {
        // Si hay una fecha de inicio, es obvio que se preparó.
        // No necesito que nadie me lo diga por parámetro.
        return this.fechaInicioCocina != null;
    }
}

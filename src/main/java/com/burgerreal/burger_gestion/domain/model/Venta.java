package com.burgerreal.burger_gestion.domain.model;

import com.burgerreal.burger_gestion.domain.enums.EstadoVenta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public record Venta (
     Long id,
     LocalDateTime fecha,
     LocalDateTime fechaInicioCocina,
     LocalDateTime fechaEntregaCliente,
     BigDecimal montoTotalBruto,
     BigDecimal costoTotalInsumos,
     BigDecimal comisionPasarela,
     BigDecimal gananciaNeta,
     boolean pagoConfirmado,
     Long cargoPorAnulacionCocina,
     EstadoVenta estado,
     MetodoPago metodoPago,
     List<ItemVenta> items
){
    //Nueva venta flujo creacion
    public static Venta nuevaVenta(BigDecimal comision, BigDecimal neto, BigDecimal montoTotalBruto,
                                   BigDecimal costoTotalInsumos, boolean pagoConfirmado, MetodoPago metodoPago,
                                   List<ItemVenta> items){

        // REGLA LOGÍSTICA: Si TODOS los ítems ya están entregados, es una venta directa
        boolean esVentaDirecta = items != null && items.stream().allMatch(ItemVenta::entregado);

        // Si es venta directa, nace COMPLETADA de inmediato, si no, se queda PENDIENTE de cocina
        EstadoVenta estadoInicial = esVentaDirecta ? EstadoVenta.COMPLETADA : EstadoVenta.PENDIENTE;

        // Si es venta directa, la fecha de entrega al cliente es AHORA mismo
        LocalDateTime fechaEntrega = esVentaDirecta ? LocalDateTime.now() : null;

        return new Venta(
                null,
                null,
                null,
                fechaEntrega,
                montoTotalBruto.setScale(0, RoundingMode.CEILING),
                costoTotalInsumos.setScale(0, RoundingMode.CEILING),
                comision,
                neto.setScale(0, RoundingMode.CEILING),
                pagoConfirmado,
                0L,
                estadoInicial,
                metodoPago,
                items
        );
    }

    public Venta anular(BigDecimal comisionCocinero) {
        if (this.estado == EstadoVenta.ANULADA)
            throw new IllegalStateException("La venta ya está anulada");

        if (this.estado == EstadoVenta.COMPLETADA)
            throw new IllegalStateException("No se puede anular una venta que ya ha sido completada.");
        return new Venta(
                this.id,
                this.fecha,
                this.fechaInicioCocina,
                this.fechaEntregaCliente,
                this.montoTotalBruto,
                this.costoTotalInsumos,
                this.comisionPasarela,
                this.gananciaNeta,
                this.pagoConfirmado,
                comisionCocinero.setScale(0, RoundingMode.HALF_UP).longValue(),
                EstadoVenta.ANULADA,   // Lo único que realmente cambia
                this.metodoPago,
                this.items
        );
    }

    // Metodo de consulta simple para reglas de negocio
    public BigDecimal calcularPerdidaInsumosSiEstaPreparada() {
        boolean inicioCocina = this.estado == EstadoVenta.EN_COCINA
                || this.estado == EstadoVenta.LISTO_PARA_ENTREGA;
        return inicioCocina ? this.costoTotalInsumos : BigDecimal.ZERO;
    }

    public Venta iniciarPreparacionVenta(){
        if(this.estado == EstadoVenta.ANULADA)
            throw new IllegalStateException("No se puede iniciar la preparacion: la venta está ANULADA.");

        if(this.estado != EstadoVenta.PENDIENTE)
            throw new IllegalStateException("Solo se puede iniciar preparación de ventas PENDIENTES.");

        return new Venta(
                this.id, this.fecha, LocalDateTime.now(), this.fechaEntregaCliente,
                this.montoTotalBruto, this.costoTotalInsumos, this.comisionPasarela,
                this.gananciaNeta, this.pagoConfirmado, this.cargoPorAnulacionCocina,
                EstadoVenta.EN_COCINA, this.metodoPago, this.items
        );
    }

    public Venta terminarCocina() {
        if (this.estado != EstadoVenta.EN_COCINA) {
            throw new IllegalStateException("No se puede terminar la cocina: el pedido no está EN_COCINA.");
        }

        // REGLA LOGÍSTICA: Mutamos los ítems de cocina a entregado = true
        List<ItemVenta> itemsActualizados = this.items().stream()
                .map(item -> new ItemVenta(
                        item.id(),
                        item.producto(),
                        item.cantidad(),
                        item.precioVentaHistorico(),
                        item.costoProduccionHistorico(),
                        true // <--- Salió de cocina, por ende este ítem ya está listo/entregado
                )).toList();

        return new Venta(
                this.id, this.fecha, this.fechaInicioCocina, this.fechaEntregaCliente,
                this.montoTotalBruto, this.costoTotalInsumos, this.comisionPasarela,
                this.gananciaNeta, this.pagoConfirmado, this.cargoPorAnulacionCocina,
                EstadoVenta.LISTO_PARA_ENTREGA, // <--- Pasa a LISTO_PARA_ENTREGA
                this.metodoPago, itemsActualizados // Le inyectamos la lista con los ítems ya listos
        );
    }

    public Venta completarVenta() {
        if (this.estado != EstadoVenta.LISTO_PARA_ENTREGA) {
            throw new IllegalStateException("No se puede completar: el pedido debe estar LISTO_PARA_ENTREGA.");
        }

        return new Venta(
                this.id, this.fecha, this.fechaInicioCocina, LocalDateTime.now(),
                this.montoTotalBruto, this.costoTotalInsumos, this.comisionPasarela,
                this.gananciaNeta, this.pagoConfirmado, this.cargoPorAnulacionCocina,
                EstadoVenta.COMPLETADA, this.metodoPago, this.items
        );
    }

    public Venta editarVenta(BigDecimal nuevoMontoBruto, BigDecimal nuevoCostoInsumos, BigDecimal nuevaComision,
                             BigDecimal nuevaGananciaNeta, MetodoPago nuevoMetodoPago) {

        if (this.estado != EstadoVenta.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden editar ventas que estén en estado PENDIENTE.");
        }

        return new Venta(
                this.id, this.fecha, this.fechaInicioCocina, this.fechaEntregaCliente, nuevoMontoBruto,
                nuevoCostoInsumos, nuevaComision, nuevaGananciaNeta, this.pagoConfirmado,
                this.cargoPorAnulacionCocina, this.estado, nuevoMetodoPago, this.items
        );
    }
}

package com.burgerreal.burger_gestion.application.service.venta;

import com.burgerreal.burger_gestion.application.port.in.venta.RegistrarVentaUseCase;
import com.burgerreal.burger_gestion.domain.model.*;
import com.burgerreal.burger_gestion.domain.port.out.*;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RegistrarVentaService implements RegistrarVentaUseCase {

    private final MetodoPagoRepositoryPort metodoPagoRepositoryPort;
    private final VentaRepositoryPort ventaRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort; // Necesario para ver recetas
    private final InsumoRepositoryPort insumoRepositoryPort;     // Necesario para restar stock
    private final RecetaRepositoryPort recetaRepositoryPort;
    private final CalculoVentaService calculoVentaService;

    public RegistrarVentaService(MetodoPagoRepositoryPort metodoPagoRepositoryPort, VentaRepositoryPort ventaRepositoryPort, ProductoRepositoryPort productoRepositoryPort, InsumoRepositoryPort insumoRepositoryPort, RecetaRepositoryPort recetaRepositoryPort, CalculoVentaService calculoVentaService) {
        this.metodoPagoRepositoryPort = metodoPagoRepositoryPort;
        this.ventaRepositoryPort = ventaRepositoryPort;
        this.productoRepositoryPort = productoRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
        this.recetaRepositoryPort = recetaRepositoryPort;
        this.calculoVentaService = calculoVentaService;
    }

    @Override
    @Transactional
    public Venta ejecutar(Command command) {
        MetodoPago metodoPago = metodoPagoRepositoryPort.buscarPorId(command.metodoPagoId()).
                orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con ID:" + command.metodoPagoId()));

        BigDecimal costoTotalInsumos = BigDecimal.ZERO;
        BigDecimal montoTotalBruto = BigDecimal.ZERO;

        for (ItemVenta item : command.items()) {
            Producto producto = productoRepositoryPort.buscarPorId(item.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal cantidadItems = BigDecimal.valueOf(item.cantidad());

            // 1. ACUMULAR MONTO BRUTO (Corregido: usamos .add)
            BigDecimal subtotalProducto = BigDecimal.valueOf(producto.precioVenta()).multiply(cantidadItems);
            montoTotalBruto = montoTotalBruto.add(subtotalProducto);

            // 2. ACUMULAR COSTO PRODUCCIÓN (Correcto: ya viene pre-calculado)
            costoTotalInsumos = costoTotalInsumos.add(
                    producto.costoProduccionTotal().multiply(cantidadItems)
            );

            for (ProductoInsumo recetaItem : producto.ingredientes()) {
                Insumo insumo = recetaItem.insumo();

                if (insumo.esInventariable()) {
                    double rendimiento = recetaRepositoryPort.buscarPorId(insumo.recetaId()).
                            orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado con ID:" + command.metodoPagoId())).rendimiento();

                    // Cantidad necesaria la que va al plato! papas 150gr palta 75. etc
                    BigDecimal cantidadNecesaria = BigDecimal.valueOf(recetaItem.cantidad())
                            .multiply(BigDecimal.valueOf(item.cantidad()));

                    // Reducimos stock (esto internamente debería manejar BigDecimal o Double)
                    Insumo insumoActualizado = insumo.reducirStock(cantidadNecesaria.doubleValue()/rendimiento);
                    insumoRepositoryPort.guardar(insumoActualizado);
                }
            }
        }

        // Cálculos de comisiones y ganancias (también deben recibir BigDecimal)
        BigDecimal comision = calculoVentaService.calcularComision(montoTotalBruto, metodoPago);

        // Aquí es donde aplicamos tu lógica: Ganancia Neta final con redondeo
        // Usamos CEILING para redondear hacia arriba si hay decimales en el neto
        BigDecimal neto = calculoVentaService.calcularGananciaNeta(montoTotalBruto, costoTotalInsumos, comision)
                .setScale(0, RoundingMode.CEILING);

        Venta nuevaVenta = Venta.nuevaVenta(
                comision,
                neto,
                montoTotalBruto.setScale(0, RoundingMode.CEILING), // Precio final venta redondeado
                costoTotalInsumos, // Este lo podemos dejar con decimales para reportes de costos precisos
                command.pagoConfirmado(),
                metodoPago);

        return ventaRepositoryPort.guardar(nuevaVenta);
    }
}

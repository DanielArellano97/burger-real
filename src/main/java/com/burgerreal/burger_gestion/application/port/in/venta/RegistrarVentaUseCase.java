package com.burgerreal.burger_gestion.application.port.in.venta;

import com.burgerreal.burger_gestion.domain.model.Venta;

import java.util.List;

public interface RegistrarVentaUseCase {

    Venta ejecutar(Command command);

    record Command(
            List<ItemVentaCommand> items,
            boolean pagoConfirmado,
            Long metodoPagoId,
            boolean esExpress
    ){}

    record ItemVentaCommand(
            Long productoId,
            Integer cantidad
    ){}
}

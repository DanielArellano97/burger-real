package com.burgerreal.burger_gestion.application.port.in.merma;

public interface RegistrarMermaUseCase {

    void ejecutar(Command command);

    record Command(
            Long insumoId,
            Double cantidad,
            String motivo
    ) {}
}

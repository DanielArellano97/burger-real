package com.burgerreal.burger_gestion.application.service.merma;

import com.burgerreal.burger_gestion.application.port.in.merma.RegistrarMermaUseCase;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.model.Merma;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.MermaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class RegistrarMermaService implements RegistrarMermaUseCase {

    private final MermaRepositoryPort mermaRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;

    public RegistrarMermaService(MermaRepositoryPort mermaRepositoryPort, InsumoRepositoryPort insumoRepositoryPort) {
        this.mermaRepositoryPort = mermaRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
    }

    @Override
    @Transactional
    public void ejecutar(Command command) {
        // 1. Buscar el insumo
        Insumo insumo = insumoRepositoryPort.buscarPorId(command.insumoId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        // 2. Usar la lógica de dominio de Insumo para reducir el stock
        Insumo insumoActualizado = insumo.reducirStock(command.cantidad());

        // 3. Crear el objeto Merma con los datos del insumo actual
        Merma merma = Merma.crear(
                insumo.id(),
                insumo.nombre(),
                command.cantidad(),
                command.motivo(),
                insumo.costoUnitario()
        );

        // 4. Persistir ambos cambios
        insumoRepositoryPort.guardar(insumoActualizado);
        mermaRepositoryPort.guardar(merma);
    }
}

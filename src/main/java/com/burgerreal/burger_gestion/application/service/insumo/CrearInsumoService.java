package com.burgerreal.burger_gestion.application.service.insumo;

import com.burgerreal.burger_gestion.application.port.in.insumo.CrearInsumoUseCase;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class CrearInsumoService implements CrearInsumoUseCase {

    private final InsumoRepositoryPort insumoRepositoryPort;

    public CrearInsumoService(InsumoRepositoryPort insumoRepositoryPort) {
        this.insumoRepositoryPort = insumoRepositoryPort;
    }

    @Override
    @Transactional
    public Insumo ejecutar(Insumo insumo) {

        if(insumoRepositoryPort.existePorNombre(insumo.nombre())){
            throw new IllegalStateException("Ya existe un insumo con el nombre: " + insumo.nombre());
        }

        return insumoRepositoryPort.guardar(insumo);
    }
}

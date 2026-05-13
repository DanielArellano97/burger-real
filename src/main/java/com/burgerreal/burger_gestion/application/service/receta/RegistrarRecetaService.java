package com.burgerreal.burger_gestion.application.service.receta;

import com.burgerreal.burger_gestion.application.port.in.receta.RegistrarRecetaUseCase;
import com.burgerreal.burger_gestion.application.service.ProduccionService;
import com.burgerreal.burger_gestion.domain.model.Insumo;
import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.model.RecetaIngredientes;
import com.burgerreal.burger_gestion.domain.port.out.InsumoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.ProductoMercadoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.RecetaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class RegistrarRecetaService implements RegistrarRecetaUseCase {

    private final RecetaRepositoryPort recetaRepositoryPort;
    private final ProductoMercadoRepositoryPort productoMercadoRepositoryPort;
    private final InsumoRepositoryPort insumoRepositoryPort;
    private final ProduccionService produccionService;

    public RegistrarRecetaService(RecetaRepositoryPort recetaRepositoryPort, ProductoMercadoRepositoryPort productoMercadoRepositoryPort, InsumoRepositoryPort insumoRepositoryPort, ProduccionService produccionService) {
        this.recetaRepositoryPort = recetaRepositoryPort;
        this.productoMercadoRepositoryPort = productoMercadoRepositoryPort;
        this.insumoRepositoryPort = insumoRepositoryPort;
        this.produccionService = produccionService;
    }

    @Override
    @Transactional
    public Receta ejecutar(Receta receta) {
        // 1. Recorremos los ingredientes que vienen "vacíos" (solo con ID)
        List<RecetaIngredientes> ingredientesCompletos = receta.ingredientes().stream()
                .map(ing -> {
                    // 2. Buscamos el producto real en el mercado
                    ProductoMercado productoReal = productoMercadoRepositoryPort.obtenerPorId(ing.productoMercado().id())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + ing.productoMercado().id()));

                    // 3. Creamos el ingrediente con la info completa
                    return  RecetaIngredientes.crear(productoReal, ing.cantidad());
                }).toList();

        // 4. Creamos la nueva instancia de Receta con los ingredientes ya "nutridos"
        Receta recetaListaParaGuardar = Receta.crearReceta(
                receta.nombreInsumoFinal(),
                receta.categoriaInsumo(),
                receta.unidadMedida(),
                receta.rendimiento(),
                receta.factorGas(),
                receta.factorAceite(),
                ingredientesCompletos
        );

        Receta recetaGuardada = recetaRepositoryPort.registrar(recetaListaParaGuardar);

        // Creamos una versión final que tenga el ID de la DB pero los ingredientes completos
        Receta recetaCompletaConId = Receta.crearReceta(
                recetaGuardada.id(), // El ID real de la DB
                recetaListaParaGuardar.nombreInsumoFinal(),
                recetaListaParaGuardar.categoriaInsumo(),
                recetaListaParaGuardar.unidadMedida(),
                recetaListaParaGuardar.rendimiento(),
                recetaListaParaGuardar.factorGas(),
                recetaListaParaGuardar.factorAceite(),
                recetaListaParaGuardar.ingredientes()
        );

        BigDecimal costoUnitarioReal = produccionService.calcularCostoPorGramo(recetaCompletaConId);

        // 5. CREACIÓN AUTOMÁTICA DEL INSUMO
        // Aquí es donde sucede la magia de Burger Real
        Insumo nuevoInsumo = Insumo.nuevoInsumo(
                recetaGuardada.nombreInsumoFinal(),
                costoUnitarioReal,
                0,    // Stock inicial
                10,   // Stock mínimo sugerido
                receta.unidadMedida(), // Podrías sacarlo de un campo en la receta si quieres
                recetaGuardada.categoriaInsumo(),
                true, // Es inventariable por defecto
                recetaGuardada.id() // Vínculo con la receta recién creada
        );

        insumoRepositoryPort.guardar(nuevoInsumo);

        // 5. El Adapter ahora recibe la data ya masticada.
        return recetaGuardada;
    }
}

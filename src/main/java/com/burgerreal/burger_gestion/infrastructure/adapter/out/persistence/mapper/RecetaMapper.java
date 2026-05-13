package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.model.ProductoMercado;
import com.burgerreal.burger_gestion.domain.model.Receta;
import com.burgerreal.burger_gestion.domain.model.RecetaIngredientes;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta.RequestReceta;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.receta.ResponseRecetaDetalle;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.RecetaIngredientesEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecetaMapper {

    private final RecetaIngredientesMapper recetaIngredientesMapper;

    public RecetaMapper(RecetaIngredientesMapper recetaIngredientesMapper) {
        this.recetaIngredientesMapper = recetaIngredientesMapper;
    }

    public Receta toDominio(RecetaEntity entity){
        if(entity == null) return null;
        return new Receta(
                entity.getId(),
                entity.getNombreInsumoFinal(),
                entity.getCategoriaInsumo(),
                entity.getUnidadMedida(),
                entity.getRendimiento(),
                entity.getFactorGas(),
                entity.getFactorAceite(),
                entity.getIngredientes().stream().map(recetaIngredientesMapper::toDominio).toList()
        );
    }

    public RecetaEntity toEntity(Receta dominio) {
        if (dominio == null) return null;

        // 1. Creamos la "cabecera" de la receta
        RecetaEntity recetaEntity = new RecetaEntity(
                dominio.nombreInsumoFinal(),
                dominio.categoriaInsumo(),
                dominio.unidadMedida(),
                dominio.rendimiento(),
                dominio.factorGas(),
                dominio.factorAceite()
        );

        // 2. Mapeamos los ingredientes y les pasamos la recetaEntity que acabamos de crear
        if (dominio.ingredientes() != null) {
            List<RecetaIngredientesEntity> ingredientesEntities = dominio.ingredientes().stream()
                    .map(ing -> recetaIngredientesMapper.toEntity(ing, recetaEntity))
                    .toList();

            // 3. Le entregamos la lista de hijos a la entidad padre
            recetaEntity.setIngredientes(ingredientesEntities);
        }

        return recetaEntity;
    }

    public Receta toDominio(RequestReceta request){
        if(request == null) return null;

        return Receta.crearReceta(
                request.nombreInsumoFinal(),
                request.categoriaInsumo(),
                request.unidadMedida(),
                request.rendimiento(),
                request.factorGas(),
                request.factorAceite(),
                request.ingredientes().stream().map(reqIng ->
                        new RecetaIngredientes(
                                null,
                                ProductoMercado.crearNuevoSoloId(reqIng.productoMercadoId()), // <--- CREAS UN OBJETO "VACÍO" SOLO CON ID
                                reqIng.cantidad()
                        )
                ).toList()

        );
    }

    public ResponseRecetaDetalle toResponse(Receta dominio){
        if(dominio == null) return null;

        return new ResponseRecetaDetalle(
                dominio.id(),
                dominio.nombreInsumoFinal(),
                dominio.rendimiento(),
                dominio.factorGas(),
                dominio.factorAceite(),
                dominio.ingredientes()
        );
    }
}

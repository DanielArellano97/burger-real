package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper;

import com.burgerreal.burger_gestion.domain.enums.CategoriaInsumo;
import com.burgerreal.burger_gestion.domain.enums.CategoriaProducto;
import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.model.ProductoInsumo;
import com.burgerreal.burger_gestion.domain.model.ProductoVariante;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.in.web.dto.producto.ResponseResumenProducto;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoVarianteEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductoMapper {

    private final ProductoInsumoMapper productoInsumoMapper;

    public ProductoMapper(ProductoInsumoMapper productoInsumoMapper) {
        this.productoInsumoMapper = productoInsumoMapper;
    }

    public ProductoEntity toEntity(Producto dominio){
        if(dominio == null) return null;

        ProductoEntity entity = new ProductoEntity();

        if (dominio.id() != null) {
            entity.setId(dominio.id());
        }

        entity.setNombre(dominio.nombre());
        entity.setDescripcion(dominio.descripcion());
        entity.setPrecioVenta(dominio.precioVenta());
        entity.setCostoProduccionTotal(dominio.costoProduccionTotal());
        entity.setImagenUrl(dominio.imagenUrl());
        entity.setDisponible(dominio.disponible());
        entity.setRequiereCocina(dominio.requiereCocina());
        entity.setCategoria(dominio.categoria());

        entity.setIngredientes(dominio.ingredientes() != null
                ? dominio.ingredientes().stream().map(productoInsumoMapper::toEntity).toList()
                : Collections.emptyList());

        // 🌟 Mapeamos las variantes asignándole el producto padre (Entity) para mantener la integridad referencial
        if (dominio.variantes() != null) {
            List<ProductoVarianteEntity> varianteEntities = dominio.variantes().stream()
                    .map(v -> {
                        ProductoVarianteEntity ve = toVarianteEntity(v);
                        ve.setProducto(entity);
                        return ve;
                    }).toList();
            entity.setVariantes(varianteEntities);
        } else {
            entity.setVariantes(Collections.emptyList());
        }

        return entity;
    }

    public Producto toDominio(ProductoEntity entity){
        if(entity == null) return null;

        return new Producto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecioVenta(),
                entity.getCostoProduccionTotal(),
                entity.getImagenUrl(),
                entity.isDisponible(),
                entity.isRequiereCocina(),
                entity.getCategoria(),
                entity.getIngredientes() != null
                        ? entity.getIngredientes().stream().map(productoInsumoMapper::toDominio).toList()
                        : Collections.emptyList(),
                entity.getVariantes() != null
                        ? entity.getVariantes().stream().map(this::toVarianteDominio).toList()
                        : Collections.emptyList() // 🌟 Fix completo para cargar del Dominio
        );
    }

    public List<ResponseResumenProducto> toResponseResumenProducto(List<Producto> dominio) {
        if (dominio == null) return Collections.emptyList();

        return dominio.stream()
                .map(p -> {
                    // 🌟 Filtro inteligente: Solo las hamburguesas llevan la lista de ingredientes al modal
                    List<ResponseResumenProducto.IngredienteResumenDTO> ingredientesDTO =
                            (p.categoria() == CategoriaProducto.HAMBURGUESA)
                                    ? mapearIngredientes(p.ingredientes())
                                    : Collections.emptyList(); // [] para papas, aros, bebidas, etc.

                    // 🌟 Mapeamos las variantes para el DTO resumido que usará Clau en el catálogo
                    List<ResponseResumenProducto.VarianteResumenDTO> variantesDTO =
                            (p.variantes() != null)
                                    ? p.variantes().stream().map(this::mapearVarianteDTO).toList()
                                    : Collections.emptyList();

                    // 2. Instanciamos el DTO principal limpio
                    return new ResponseResumenProducto(
                            p.id(),
                            p.nombre(),
                            p.descripcion(),
                            p.precioVenta(),
                            p.imagenUrl(),
                            p.categoria(),
                            p.disponible(),
                            ingredientesDTO,
                            variantesDTO
                    );
                })
                .toList();
    }

    /**
     * Metodo auxiliar encargado de transformar los insumos y calcular
     * dinámicamente las reglas de interacción para el frontend.
     */
    private List<ResponseResumenProducto.IngredienteResumenDTO> mapearIngredientes(List<ProductoInsumo> ingredientes) {
        if (ingredientes == null) return Collections.emptyList();

        return ingredientes.stream()
                // El packaging se va siempre, no va al catálogo de cara al cliente
                .filter(i -> i.insumo().categoria() != CategoriaInsumo.PACKAGING)
                .map(i -> {
                    String nombreLower = i.insumo().nombre().toLowerCase();

                    // 🌟 Extraemos el flag de la base de datos a través del objeto de dominio
                    // Nota: Asegúrate de si en tu clase Insumo del dominio se llama esComercializableExtra() o getEsComercializableExtra()
                    boolean esComercializable = i.insumo().esComercializadoraExtra();

                    // Calculamos dinámicamente los permisos de los botones
                    boolean permiteQuitar = definirPermiteQuitar(nombreLower);

                    // 🌟 Le pasamos el flag al metodo encargado de la suma
                    boolean permiteAgregar = definirPermiteAgregar(nombreLower, esComercializable);

                    return new ResponseResumenProducto.IngredienteResumenDTO(
                            i.insumo().id(),
                            i.insumo().nombre(),
                            i.insumo().valorExtra(),
                            permiteQuitar,
                            permiteAgregar
                    );
                })
                .toList();
    }

    // 🧠 Reglas exclusivas para el botón de restar (-) en el modal
    private boolean definirPermiteQuitar(String nombreInsumo) {
        if (nombreInsumo.contains("pan") || nombreInsumo.contains("papas frita")) {
            return false;
        }
        if (nombreInsumo.contains("medallón de carne") || nombreInsumo.contains("carne")) {
            return false; // La carne base no se saca
        }
        return true; // Toppings y salsas sí se pueden quitar
    }

    // 🧠 Reglas exclusivas para el botón de sumar (+) en el modal
// 🌟 Ahora recibe la configuración de la BD para bloquear las bebidas o deditos extras si están en false
    private boolean definirPermiteAgregar(String nombreInsumo, boolean esComercializable) {
        // 🔒 Candado de seguridad: Si en la BD dice que NO se vende como extra, se bloquea el "+" de inmediato
        if (!esComercializable) {
            return false;
        }

        if (nombreInsumo.contains("pan") || nombreInsumo.contains("papas frita")) {
            return false; // No se pueden agregar más panes ni papas gratis desde aquí
        }
        return true; // Carne, quesos y salsas comerciales se pueden duplicar
    }

    // 🌟 Mapeador de Variante de Dominio a DTO de Respuesta para el Front
    private ResponseResumenProducto.VarianteResumenDTO mapearVarianteDTO(ProductoVariante v) {
        return new ResponseResumenProducto.VarianteResumenDTO(
                v.id(),
                v.nombre(),
                v.nombreCorto(),
                v.precioExtra()
        );
    }

    // 🌟 Mapeador de Variante Dominio a Entity
    private ProductoVarianteEntity toVarianteEntity(ProductoVariante dominio) {
        if (dominio == null) return null;
        return new ProductoVarianteEntity(
                dominio.id(),
            dominio.nombre(),
            dominio.nombreCorto(),
            dominio.precioExtra());
    }

    // 🌟 Mapeador de Variante Entity a Dominio
    private ProductoVariante toVarianteDominio(ProductoVarianteEntity entity) {
        if (entity == null) return null;
        return new ProductoVariante(
                entity.getId(),
                entity.getNombre(),
                entity.getNombreCorto(),
                entity.getPrecioExtra()
        );
    }

    public ResponseProducto toResponseProducto(Producto dominio) {
        if (dominio == null) return null;

        return new ResponseProducto(
                dominio.id(),
                dominio.nombre(),
                dominio.descripcion(),
                dominio.precioVenta(),
                dominio.costoProduccionTotal(),
                dominio.imagenUrl(),
                dominio.disponible(),
                dominio.categoria(),
                dominio.ingredientes().stream().map(productoInsumoMapper::toResponse).toList()
        );
    }

    public List<Producto> toDominioList(List<ProductoEntity> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(this::toDominio) // Reutiliza el metodo que ya escribiste
                .toList();
    }
}

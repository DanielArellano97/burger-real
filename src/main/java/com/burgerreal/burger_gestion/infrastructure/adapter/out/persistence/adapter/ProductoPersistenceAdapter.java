package com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.adapter;

import com.burgerreal.burger_gestion.domain.model.Producto;
import com.burgerreal.burger_gestion.domain.port.out.ProductoRepositoryPort;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.burgerreal.burger_gestion.infrastructure.adapter.out.persistence.repository.JpaProductoRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ProductoPersistenceAdapter implements ProductoRepositoryPort {

    private final JpaProductoRepository jpaProductoRepository;
    private final ProductoMapper productoMapper;

    public ProductoPersistenceAdapter(JpaProductoRepository jpaProductoRepository, ProductoMapper productoMapper) {
        this.jpaProductoRepository = jpaProductoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoEntity productoAGuardar = productoMapper.toEntity(producto);
        ProductoEntity productoGuardado = jpaProductoRepository.save(productoAGuardar);
        return productoMapper.toDominio(productoGuardado);
    }

    @Override
    public List<Producto> listarTodos() {
        // 1. Buscas de la BDD ordenados por ID
        List<ProductoEntity> productosEncontrados = jpaProductoRepository.findAllByOrderByIdAsc();

        // 2. Transformas toda la lista al Dominio usando tu mapper actual
        List<Producto> productosDominio = productoMapper.toDominioList(productosEncontrados);

        // 3. Revisas el reloj del servidor
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();

        // 🧪 TRUCO PARA PROBAR HOY MISMO (Jueves):
        // Cambia DayOfWeek.FRIDAY por DayOfWeek.THURSDAY temporalmente.
        // Así simulas que hoy ya es fin de semana y puedes ver si se borra el precio oferta.
        boolean esFinDeSemana = diaActual == DayOfWeek.FRIDAY ||
                diaActual == DayOfWeek.SATURDAY ||
                diaActual == DayOfWeek.SUNDAY;

        // 4. Si es fin de semana, limpiamos el precio de oferta en el aire
        if (esFinDeSemana) {
            return productosDominio.stream()
                    .map(producto -> producto.conPrecioOfertaModificado(null))
                    .toList();
        }

        return productosDominio;
    }

    @Override
    public Optional<Producto> buscarPorId(Long id){
        return jpaProductoRepository.findById(id).map(productoMapper::toDominio);

    }

    @Override
    public void eliminar(Long id) {
        jpaProductoRepository.deleteById(id);
    }
}

package com.burgerreal.burger_gestion.infrastructure.config;

import com.burgerreal.burger_gestion.application.service.merma.ListarMermasService;
import com.burgerreal.burger_gestion.application.service.ProduccionService;
import com.burgerreal.burger_gestion.application.service.merma.RegistrarMermaService;
import com.burgerreal.burger_gestion.application.service.compensacion_venta.BuscarCompensacionVentaPorIdVentaService;
import com.burgerreal.burger_gestion.application.service.compensacion_venta.RegistrarCompensacionVentaService;
import com.burgerreal.burger_gestion.application.service.insumo.CrearInsumoService;
import com.burgerreal.burger_gestion.application.service.insumo.ListarInsumosService;
import com.burgerreal.burger_gestion.application.service.producto.BuscarProductoPorIdService;
import com.burgerreal.burger_gestion.application.service.producto.ListarProductosService;
import com.burgerreal.burger_gestion.application.service.producto.RegistrarProductoService;
import com.burgerreal.burger_gestion.application.service.producto_mercado.BuscarProductoMercadoPorIdService;
import com.burgerreal.burger_gestion.application.service.producto_mercado.ListarParametrosMercadoService;
import com.burgerreal.burger_gestion.application.service.producto_mercado.RegistrarProductoMercadoService;
import com.burgerreal.burger_gestion.application.service.receta.BuscarRecetaPorIdService;
import com.burgerreal.burger_gestion.application.service.receta.ListarRecetasService;
import com.burgerreal.burger_gestion.application.service.receta.RegistrarRecetaService;
import com.burgerreal.burger_gestion.application.service.venta.*;
import com.burgerreal.burger_gestion.domain.port.out.*;
import com.burgerreal.burger_gestion.domain.services.CalculoVentaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CalculoVentaService calculoVentaService() {
        return new CalculoVentaService();
    }

    @Bean
    public RegistrarVentaService registrarVentaService(
            VentaRepositoryPort ventaRepositoryPort,
            MetodoPagoRepositoryPort metodoPagoRepositoryPort,
            ProductoRepositoryPort productoRepositoryPort,
            InsumoRepositoryPort insumoRepositoryPort,
            RecetaRepositoryPort recetaRepositoryPort,
            CalculoVentaService calculoVentaService) {
        return new RegistrarVentaService(metodoPagoRepositoryPort, ventaRepositoryPort, productoRepositoryPort, insumoRepositoryPort, recetaRepositoryPort, calculoVentaService);
    }

    @Bean
    public ListarVentasService listarVentasService(VentaRepositoryPort ventaRepositoryPort){
        return new ListarVentasService(ventaRepositoryPort);
    }

    @Bean
    public EliminarVentaPorIdService eliminarVentaPorIdService(VentaRepositoryPort ventaRepositoryPort, CompensacionVentaRepositoryPort compensacionVentaRepositoryPort){
        return new EliminarVentaPorIdService(ventaRepositoryPort, compensacionVentaRepositoryPort);
    }

    @Bean
    public BuscarVentaPorIdService buscarVentaPorIdService(VentaRepositoryPort ventaRepositoryPort){
        return new BuscarVentaPorIdService(ventaRepositoryPort);
    }

    @Bean
    public AnularVentaPorIdService anularVentaPorIdService(VentaRepositoryPort ventaRepositoryPort, CompensacionVentaRepositoryPort compensacionVentaRepositoryPort, CalculoVentaService calculoVentaService){
        return new AnularVentaPorIdService(ventaRepositoryPort,compensacionVentaRepositoryPort, calculoVentaService);
    }

    @Bean
    public RegistrarCompensacionVentaService registrarCompensacionVentaService(CompensacionVentaRepositoryPort compensacionVentaRepositoryPort){
        return new RegistrarCompensacionVentaService(compensacionVentaRepositoryPort);
    }

    @Bean
    public BuscarCompensacionVentaPorIdVentaService buscarCompensacionVentaPorIdVentaService(CompensacionVentaRepositoryPort compensacionVentaRepositoryPort){
        return new BuscarCompensacionVentaPorIdVentaService(compensacionVentaRepositoryPort);
    }

    @Bean
    public CompletarVentaService completarVentaService(VentaRepositoryPort ventaRepositoryPort){
        return new CompletarVentaService(ventaRepositoryPort);
    }

    @Bean
    public IniciarCocinaVentaService iniciarCocinaVentaService(VentaRepositoryPort ventaRepositoryPort){
        return new IniciarCocinaVentaService(ventaRepositoryPort);
    }

    @Bean
    public EditarVentaService editarVentaService(VentaRepositoryPort ventaRepositoryPort, CalculoVentaService calculoVentaService, MetodoPagoRepositoryPort metodoPagoRepositoryPort){
        return new EditarVentaService(ventaRepositoryPort, calculoVentaService, metodoPagoRepositoryPort);
    }

    @Bean
    public ConsultarReporteVentasService consultarReporteVentasService(VentaRepositoryPort ventaRepositoryPort){
        return new ConsultarReporteVentasService(ventaRepositoryPort);
    }

    @Bean
    public CrearInsumoService crearInsumoService(InsumoRepositoryPort insumoRepositoryPort){
        return new CrearInsumoService(insumoRepositoryPort);
    }

    @Bean
    public ListarInsumosService listarInsumosService(InsumoRepositoryPort insumoRepositoryPort){
        return new ListarInsumosService(insumoRepositoryPort);
    }

    @Bean
    public RegistrarMermaService registrarMermaService(MermaRepositoryPort mermaRepositoryPort, InsumoRepositoryPort insumoRepositoryPort){
        return new RegistrarMermaService(mermaRepositoryPort, insumoRepositoryPort);
    }

    @Bean
    public ListarMermasService listarMermasService(MermaRepositoryPort mermaRepositoryPort){
        return new ListarMermasService(mermaRepositoryPort);
    }

    @Bean
    public RegistrarProductoService registrarProductoService(ProductoRepositoryPort productoRepositoryPort, InsumoRepositoryPort insumoRepositoryPort){
        return new RegistrarProductoService(productoRepositoryPort, insumoRepositoryPort);
    }

    @Bean
    public ListarProductosService listarProductosService(ProductoRepositoryPort productoRepositoryPort){
        return new ListarProductosService(productoRepositoryPort);
    }

    @Bean
    public BuscarProductoPorIdService buscarProductoPorIdService(ProductoRepositoryPort productoRepositoryPort){
        return new BuscarProductoPorIdService(productoRepositoryPort);
    }

    @Bean
    public ProduccionService produccionService(ProductoMercadoRepositoryPort productoMercadoRepositoryPort){
        return new ProduccionService(productoMercadoRepositoryPort);
    }

    @Bean
    public RegistrarProductoMercadoService registrarParametroMercadoService(ProductoMercadoRepositoryPort productoMercadoRepositoryPort){
        return new RegistrarProductoMercadoService( productoMercadoRepositoryPort);
    }

    @Bean
    public ListarParametrosMercadoService listarParametrosMercadoService(ProductoMercadoRepositoryPort productoMercadoRepositoryPort){
        return new ListarParametrosMercadoService(productoMercadoRepositoryPort);
    }

    @Bean
    public BuscarProductoMercadoPorIdService buscarParametroMercadoPorIdService(ProductoMercadoRepositoryPort productoMercadoRepositoryPort){
        return new BuscarProductoMercadoPorIdService(productoMercadoRepositoryPort);
    }


    @Bean
    public RegistrarRecetaService registrarRecetaService(RecetaRepositoryPort recetaRepositoryPort,
                                                         ProductoMercadoRepositoryPort productoMercadoRepositoryPort,
                                                         InsumoRepositoryPort insumoRepositoryPort,
                                                         ProduccionService produccionService){
        return new RegistrarRecetaService(recetaRepositoryPort, productoMercadoRepositoryPort, insumoRepositoryPort, produccionService);
    }

    @Bean
    public ListarRecetasService listarRecetasService(RecetaRepositoryPort recetaRepositoryPort){
        return new ListarRecetasService(recetaRepositoryPort);
    }

    @Bean
    public BuscarRecetaPorIdService buscarRecetaPorIdService(RecetaRepositoryPort recetaRepositoryPort){
        return new BuscarRecetaPorIdService(recetaRepositoryPort);
    }


}

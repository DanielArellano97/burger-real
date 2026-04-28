package com.burgerreal.burger_gestion.infrastructure.config;

import com.burgerreal.burger_gestion.application.service.compensacion_venta.BuscarCompensacionVentaPorIdVentaService;
import com.burgerreal.burger_gestion.application.service.compensacion_venta.RegistrarCompensacionVentaService;
import com.burgerreal.burger_gestion.application.service.venta.*;
import com.burgerreal.burger_gestion.domain.port.out.CompensacionVentaRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.MetodoPagoRepositoryPort;
import com.burgerreal.burger_gestion.domain.port.out.VentaRepositoryPort;
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
            CalculoVentaService calculoVentaService) {
        return new RegistrarVentaService(metodoPagoRepositoryPort, ventaRepositoryPort, calculoVentaService);
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
}

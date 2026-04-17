package com.burgerreal.burger_gestion.infrastructure.config;

import com.burgerreal.burger_gestion.application.service.RegistrarVentaService;
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
}

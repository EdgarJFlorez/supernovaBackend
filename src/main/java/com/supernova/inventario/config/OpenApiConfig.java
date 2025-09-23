package com.supernova.inventario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Inventario API",
                version = "1.0",
                description = "API local de inventario (SUPERNOVA TECHNOLOGY)")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi docsTestApi() {
        return GroupedOpenApi.builder()
                .group("docstest")
                .pathsToMatch("/api/docstest/**")
                .build();
    }

    @Bean
    public GroupedOpenApi movimientosApi() {
        return GroupedOpenApi.builder()
                .group("movimientos")
                .pathsToMatch("/api/movimientos/**")
                .build();
    }
}

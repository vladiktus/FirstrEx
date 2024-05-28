package com.tus.springcourse.firstrex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
            .info(new Info()
                .title("Name of your service")
                .description("Description for your service")
            )
            .components(new Components()
                .addSecuritySchemes("auth", apiKeySecuritySchema())
            );
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
            .name(AUTHORIZATION)
            .description("Authorization token")
            .in(SecurityScheme.In.HEADER)
            .type(SecurityScheme.Type.APIKEY);
    }
}

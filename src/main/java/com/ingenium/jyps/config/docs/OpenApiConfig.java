package com.ingenium.jyps.config.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("JyPS API - Gestión de Usuarios") // Tu título personalizado
                        .version("1.0.0") // Tu versión real
                        .description("API profesional para la gestión de usuarios, departamentos y autenticación de la plataforma JyPS.")
                        .termsOfService("https://jyps.com/terms")
                        .contact(new Contact()
                                .name("Soporte Ingenium")
                                .email("soporte@ingenium.com")
                                .url("https://github.com/ingeniumSDS/.github"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
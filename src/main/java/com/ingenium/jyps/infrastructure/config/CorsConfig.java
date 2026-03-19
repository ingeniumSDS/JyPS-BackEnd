package com.ingenium.jyps.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos los endpoints de tu API
                .allowedOriginPatterns("*") // Permite cualquier origen (ideal para pruebas con ngrok)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Los métodos permitidos
                .allowedHeaders("*") // Permite cualquier header (incluyendo Authorization para tokens)
                .allowCredentials(false); // Cámbialo a 'true' si tu compañero te va a mandar Cookies, pero si usan Bearer Tokens en el header, 'false' o 'true' funcionan. (Nota: si usas true, allowedOriginPatterns no puede ser "*", tendrías que poner la URL exacta de su localhost).
    }
}
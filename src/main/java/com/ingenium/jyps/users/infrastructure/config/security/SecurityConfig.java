package com.ingenium.jyps.users.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Encendemos el soporte para CORS usando la configuración que definimos abajo
                .cors(Customizer.withDefaults())

                // 2. Apagamos CSRF
                .csrf(csrf -> csrf.disable())

                // 3. Lista VIP
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // 🌟 AQUÍ ESTÁ LA MAGIA DEL CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir que cualquier dominio (Angular, Postman, otra web) se conecte
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Permitir los métodos HTTP que vas a usar (incluyendo OPTIONS, que es vital para CORS)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir que el Front mande cualquier cabecera (como Authorization, Content-Type, etc)
        configuration.setAllowedHeaders(List.of("*"));

        // Aplicar estas reglas a TODAS las rutas de tu API (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
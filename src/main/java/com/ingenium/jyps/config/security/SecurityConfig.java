package com.ingenium.jyps.config.security;

import com.ingenium.jyps.users.infrastructure.adapters.in.web.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Component
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // ¡Ojo! No olvides activar el CORS en el chain
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                        "/api/v1/usuarios/login",
                                        "/api/v1/usuarios/token",
                                        "/api/v1/usuarios/setup/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",     // 💡 Añadido para evitar rebotes en la redirección
                                        "/v3/api-docs/**",
                                        "/favicon.ico"          // 💡 Opcional: evita logs de error 403 en el navegador
                                ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // 🌟 AQUÍ ESTÁ LA MAGIA DEL CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir que cualquier dominio (Angular, Postman, otra web) se conecte
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Permitir los métodos HTTP que vas a usar (incluyendo OPTIONS, que es vital para CORS)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Permitir que el Front mande cualquier cabecera (como Authorization, Content-Type, etc)
        configuration.setAllowedHeaders(List.of("*"));

        // Aplicar estas reglas a TODAS las rutas de tu API (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
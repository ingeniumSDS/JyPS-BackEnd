package com.ingenium.jyps.config.security;

import com.ingenium.jyps.users.infrastructure.adapters.in.web.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // ¡Ojo! No olvides activar el CORS en el chain
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLICAS (Siempre arriba)
                        .requestMatchers("/api/v1/usuarios/login", "/swagger-ui/**", "/v3/api-docs/**",
                                "/api/v1/usuarios/token", "/api/v1/usuarios/setup/**").permitAll()

                        // 2. RUTAS MULTI-ROL (Evitamos que un rol bloquee al otro)
                        .requestMatchers("/api/v1/justificantes/{id}/detalles", "/api/v1/pases/{id}/detalles")
                        .hasAnyRole("EMPLEADO", "JEFE_DE_DEPARTAMENTO", "ADMINISTRADOR")

                        .requestMatchers("/api/v1/{departamentoId}/usuarios")
                        .hasAnyRole("JEFE_DE_DEPARTAMENTO", "ADMINISTRADOR")

                        .requestMatchers("/api/v1/usuarios/{id}/**")
                        .hasAnyRole("JEFE_DE_DEPARTAMENTO", "ADMINISTRADOR")

                        // 3. RUTAS EXCLUSIVAS EMPLEADO
                        .requestMatchers(HttpMethod.POST, "/api/v1/justificantes", "/api/v1/pases").hasRole("EMPLEADO")
                        .requestMatchers("/api/v1/justificantes/empleado", "/api/v1/pases/empleado").hasRole("EMPLEADO")

                        // 4. RUTAS EXCLUSIVAS JEFE
                        .requestMatchers("/api/v1/justificantes/jefe", "/api/v1/justificantes/revisar").hasRole("JEFE_DE_DEPARTAMENTO")
                        .requestMatchers("/api/v1/pases/jefe", "/api/v1/pases/revisar").hasRole("JEFE_DE_DEPARTAMENTO")
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").hasRole("JEFE_DE_DEPARTAMENTO")

                        // 5. RUTAS EXCLUSIVAS SEGURIDAD / ADMIN
                        .requestMatchers("/api/v1/pases/{qr}").hasRole("GUARDIA")
                        .requestMatchers("/api/v1/departamentos/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/v1/usuarios/**").hasRole("ADMINISTRADOR") // Este actúa como "catch-all" para el resto de rutas de usuarios

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
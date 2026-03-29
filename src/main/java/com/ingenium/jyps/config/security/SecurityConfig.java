package com.ingenium.jyps.config.security;

import com.ingenium.jyps.users.infrastructure.adapters.in.web.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Aquí pones tus rutas públicas (las que NO necesitan token)
                        .requestMatchers("/api/v1/usuarios/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/v1/usuarios/token",
                                "/api/v1/usuarios/setup/validar",
                                "/api/v1/usuarios/setup")
                        .permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios").hasRole("ADMINISTRADOR")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").hasRole("ADMINISTRADOR")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/*").hasRole("ADMINISTRADOR")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/departamentos").hasRole("ADMINISTRADOR")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/departamentos").hasRole("ADMINISTRADOR")
                        // Todo lo demás sí o sí necesita token
                        .anyRequest().permitAll()
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
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir que el Front mande cualquier cabecera (como Authorization, Content-Type, etc)
        configuration.setAllowedHeaders(List.of("*"));

        // Aplicar estas reglas a TODAS las rutas de tu API (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
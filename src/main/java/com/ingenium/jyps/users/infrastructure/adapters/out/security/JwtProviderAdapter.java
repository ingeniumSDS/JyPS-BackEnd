package com.ingenium.jyps.users.infrastructure.adapters.out.security;

import com.ingenium.jyps.users.application.ports.out.JwtProviderPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;



@Component
public class JwtProviderAdapter implements JwtProviderPort {

    private final SecretKey key; // La declaramos, pero NO la inicializamos aquí

    public JwtProviderAdapter(@Value("${jwt.secret.key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());

    }

    @Override
    public String generarToken(Usuario usuario) {

        // Convertimos la lista de Roles (Enums) a lista de Strings para guardarla en el token
        List<String> rolesStr = usuario.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        long tiempoExpiracion = 24 * 60 * 60 * 1000L;
        // Tiempo de vida del token (Ej.: 24 horas)
        return Jwts.builder()
                .subject(usuario.getCorreo()) // El "sujeto" principal del token (suele ser el correo o ID)
                .claim("id", usuario.getId()) // Datos extra (claims)
                .claim("nombre", usuario.getNombre())
                .claim("apellidoPaterno", usuario.getApellidoPaterno())
                .claim("apellidoMaterno", usuario.getApellidoMaterno())
                .claim("telefono", usuario.getTelefono())
                .claim("departamentoId", usuario.getDepartamentoId())
                .claim("nombreDepartamento", usuario.getNombreDepartamento())
                .claim("roles", rolesStr) // ¡Súperimportante para los permisos después!
                .issuedAt(new Date()) // Fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion)) // Fecha de caducidad
                .signWith(key) // Sellamos el gafete con la firma del Chef
                .compact(); // Construimos el String final
    }
}

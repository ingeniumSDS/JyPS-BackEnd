package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.EstablecerPasswordUseCase;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.ValidarTokenRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstablecerPasswordService implements EstablecerPasswordUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public EstablecerPasswordService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario ejecutar(ValidarTokenRequest request) {
        Usuario usuario = usuarioRepositoryPort.findByToken(request.token())
                .orElseThrow(() -> new IllegalArgumentException("Token no válido"));

        usuario.getCuenta().validarToken();

        usuario.getCuenta().establecerPassword(passwordEncoderPort.codificar(request.password()));

        usuarioRepositoryPort.save(usuario);

        return usuario;
    }

    @Override
    public Usuario validarToken(String token) {
        Usuario usuario = usuarioRepositoryPort.findByToken(token).orElseThrow(() -> new IllegalStateException("Token no válido"));
        usuario.getCuenta().validarToken();
        return usuario;
    }
}



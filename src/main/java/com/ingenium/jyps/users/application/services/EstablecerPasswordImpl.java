package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.command.EstablecerPasswordCommand;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class EstablecerPasswordImpl implements com.ingenium.jyps.users.application.ports.in.usecases.EstablecerPasswordUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public EstablecerPasswordImpl(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario ejecutar(EstablecerPasswordCommand command) {

        Usuario usuario = usuarioRepositoryPort.findByToken(command.token())
                .orElseThrow(() -> new IllegalArgumentException("Token no válido"));
        usuario.getCuenta().esPasswordSegura(command.password());
        usuario.getCuenta().validarToken();

        usuario.getCuenta().establecerPassword(passwordEncoderPort.codificar(command.password()));

        usuarioRepositoryPort.guardar(usuario);

        return usuario;
    }

    @Override
    public Usuario validarToken(String token) {
        Usuario usuario = usuarioRepositoryPort.findByToken(token).orElseThrow(() -> new IllegalStateException("Token no válido"));
        usuario.getCuenta().validarToken();
        return usuario;
    }
}



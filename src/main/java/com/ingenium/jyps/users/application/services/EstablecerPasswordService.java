package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.command.EstablecerPasswordCommand;
import com.ingenium.jyps.users.application.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstablecerPasswordService implements com.ingenium.jyps.users.application.ports.in.usecases.EstablecerPasswordUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public Usuario ejecutar(EstablecerPasswordCommand command) {

        Usuario usuario = usuarioRepositoryPort.buscarPorToken(command.token())
                .orElseThrow(() -> new IllegalArgumentException("Token no válido"));
        usuario.getCuenta().esPasswordSegura(command.password());
        usuario.getCuenta().validarToken();

        usuario.getCuenta().establecerPassword(passwordEncoderPort.codificar(command.password()));

        usuarioRepositoryPort.crear(usuario);

        return usuario;
    }

    @Override
    public Usuario validarToken(String token) {
        Usuario usuario = usuarioRepositoryPort.buscarPorToken(token).orElseThrow(() -> new IllegalStateException("Token no válido"));
        usuario.getCuenta().validarToken();
        return usuario;
    }
}



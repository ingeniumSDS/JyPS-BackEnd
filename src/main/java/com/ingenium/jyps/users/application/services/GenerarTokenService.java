package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.GenerarTokenUseCase;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.event.TokenSolicitadoEvent;
import com.ingenium.jyps.users.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GenerarTokenService implements GenerarTokenUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Usuario ejecutar(String correo) {
        Usuario usuario = usuarioRepository.buscarPorCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.getCuenta().generarTokenAcceso();
        usuarioRepository.crear(usuario);

        publisher.publishEvent(new TokenSolicitadoEvent(
                usuario.getNombre() + " " + usuario.getApellidoPaterno() +  " " + usuario.getApellidoMaterno(),
                correo,
                usuario.getCuenta().getTokenAcceso()
        ));

        return usuario;
    }
}

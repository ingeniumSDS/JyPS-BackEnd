package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.event.TokenSolicitadoEvent;
import com.ingenium.jyps.users.domain.model.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class GenerarTokenImpl implements com.ingenium.jyps.users.application.ports.in.usecases.GenerarTokenUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ApplicationEventPublisher publisher;


    public GenerarTokenImpl(UsuarioRepositoryPort usuarioRepository,
                            ApplicationEventPublisher publisher) {
        this.usuarioRepository = usuarioRepository;
        this.publisher = publisher;
    }


    @Override
    public Usuario ejecutar(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.getCuenta().generarTokenAcceso();
        usuarioRepository.save(usuario);

        publisher.publishEvent(new TokenSolicitadoEvent(
                usuario.getNombre() + " " + usuario.getApellidoPaterno() +  " " + usuario.getApellidoMaterno(),
                correo,
                usuario.getCuenta().getTokenAcceso()
        ));

        return usuario;
    }
}

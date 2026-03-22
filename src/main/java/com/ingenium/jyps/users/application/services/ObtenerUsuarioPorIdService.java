package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.command.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdService implements ObtenerUsuarioPorIdUseCase {

    private final UsuarioRepository usuarioRepositoryPort;

    public ObtenerUsuarioPorIdService(UsuarioRepository usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario obtenerUsuarioPorId(ObtenerUsuarioPorIdCommand command) {
        return usuarioRepositoryPort.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));
    }
}

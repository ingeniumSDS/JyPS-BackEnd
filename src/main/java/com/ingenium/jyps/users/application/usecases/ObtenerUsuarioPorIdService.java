package com.ingenium.jyps.users.application.usecases;

import com.ingenium.jyps.users.application.ports.in.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.application.ports.in.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdService implements ObtenerUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ObtenerUsuarioPorIdService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario obtenerUsuarioPorId(ObtenerUsuarioPorIdCommand command) {
        return usuarioRepositoryPort.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));
    }
}

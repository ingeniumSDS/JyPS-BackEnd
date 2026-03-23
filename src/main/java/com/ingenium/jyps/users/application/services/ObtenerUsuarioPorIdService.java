package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerUsuarioPorIdUseCase;
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
    public Usuario ejecutar(Long id) {
        return usuarioRepositoryPort.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));
    }
}

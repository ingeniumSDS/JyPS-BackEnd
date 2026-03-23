package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTodosLosUsuariosService implements ObtenerTodosLosUsuariosUseCase {
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ObtenerTodosLosUsuariosService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public List<Usuario> ejecutar() {
        return usuarioRepositoryPort.findAll();
    }
}


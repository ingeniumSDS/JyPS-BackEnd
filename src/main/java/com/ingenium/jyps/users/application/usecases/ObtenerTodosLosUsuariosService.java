package com.ingenium.jyps.users.application.usecases;

import com.ingenium.jyps.users.application.ports.in.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.application.ports.in.ObtenerUsuarioPorIdCommand;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTodosLosUsuariosService implements ObtenerTodosLosUsuariosUseCase {
    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerTodosLosUsuariosService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}


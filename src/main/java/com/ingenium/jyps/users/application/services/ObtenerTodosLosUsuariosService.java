package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepository;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTodosLosUsuariosService implements ObtenerTodosLosUsuariosUseCase {
    private final UsuarioRepository usuarioRepository;

    public ObtenerTodosLosUsuariosService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}


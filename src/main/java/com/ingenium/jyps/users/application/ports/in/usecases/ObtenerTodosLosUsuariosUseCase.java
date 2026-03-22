package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;

import java.util.List;

public interface ObtenerTodosLosUsuariosUseCase {

    List<Usuario> findAll();

}

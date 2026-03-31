package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.List;

public interface ConsultarUsuariosUseCase {

    Usuario obtenerPorId(Long id);
    List<Usuario> obtenerTodos();

}

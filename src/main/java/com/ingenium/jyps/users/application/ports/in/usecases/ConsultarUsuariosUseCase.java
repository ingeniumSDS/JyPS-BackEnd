package com.ingenium.jyps.users.application.ports.in.usecases;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface ConsultarUsuariosUseCase {

    Optional<Usuario> obtenerPorId(Long id);
    List<Usuario> obtenerTodos();
    Long contarPorDepartamento(Long departamentoId);
    List<Usuario> filtrarPorDepartamento(Long departamentoId);
}

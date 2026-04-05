package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarUsuariosService implements ConsultarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;



    @Override
    public Usuario obtenerPorId(Long id) {
        Usuario usuario = usuarioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));

        Departamento departamento = departamentoRepositoryPort.buscarPorId(usuario.getDepartamentoId())
                .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));

        usuario.setNombreDepartamento(departamento.getNombre());

        return usuario;

    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepositoryPort.buscarTodos();

        usuarios.forEach(u -> {
            Departamento departamento = departamentoRepositoryPort.buscarPorId(u.getDepartamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));
            u.setNombreDepartamento(departamento.getNombre());
        });
        return usuarios;
    }

}
package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarUsuariosimpl implements ConsultarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;


    public ConsultarUsuariosimpl(UsuarioRepositoryPort usuarioRepositoryPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        Usuario usuario = usuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));

        Departamento departamento = departamentoRepositoryPort.findById(usuario.getDepartamentoId())
                .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));

        usuario.setNombreDepartamento(departamento.getNombre());

        return usuario;

    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepositoryPort.buscarTodos();

        usuarios.forEach(u -> {
            Departamento departamento = departamentoRepositoryPort.findById(u.getDepartamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));
            u.setNombreDepartamento(departamento.getNombre());
        });
        return usuarios;
    }

}
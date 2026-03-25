package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerTodosLosUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTodosLosUsuariosService implements ObtenerTodosLosUsuariosUseCase {
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    public ObtenerTodosLosUsuariosService(UsuarioRepositoryPort usuarioRepositoryPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public List<Usuario> ejecutar() {
        List<Usuario> usuarios = usuarioRepositoryPort.findAll();

        usuarios.forEach(u -> {
            Departamento departamento = departamentoRepositoryPort.findById(u.getDepartamentoId())
                    .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));
            u.setNombreDepartamento(departamento.getNombre());
        });
        return usuarios;
    }
}


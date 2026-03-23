package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ObtenerUsuarioPorIdUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdService implements ObtenerUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    public ObtenerUsuarioPorIdService(UsuarioRepositoryPort usuarioRepositoryPort, DepartamentoRepositoryPort departamentoRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.departamentoRepositoryPort = departamentoRepositoryPort;
    }

    @Override
    public Usuario ejecutar(Long id) {

        Usuario usuario = usuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));

        Departamento departamento = departamentoRepositoryPort.findById(usuario.getDepartamentoId())
                .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));

        usuario.setNombreDepartamento(departamento.getNombre());

        return usuario;
    }
}

package com.ingenium.jyps.users.application.services;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarUsuariosService implements ConsultarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;



    @Override
    public Optional<Usuario> obtenerPorId(Long id) {

        if (id == null) {
            return Optional.empty();
        }

        Usuario usuario = usuarioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ese ID no existe"));

        Departamento departamento = departamentoRepositoryPort.buscarPorId(usuario.getDepartamentoId())
                .orElseThrow(() -> new IllegalArgumentException("El departamento con ese ID no existe"));

        usuario.setNombreDepartamento(departamento.getNombre());

        return Optional.of(usuario);

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

    @Override
    public Long contarPorDepartamento(Long departamentoId) {
        return usuarioRepositoryPort.contarPorDepartamento(departamentoId);
    }

    @Override
    public List<Usuario> filtrarPorDepartamento(Long departamentoId) {
        if (departamentoRepositoryPort.buscarPorId(departamentoId).isEmpty()) {
            throw new IllegalArgumentException("El departamento con ese ID no existe");
        }
        return usuarioRepositoryPort.filtrarPorDepartamento(departamentoId);
    }


}
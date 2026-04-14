package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.AsignarJefeCommand;
import com.ingenium.jyps.departamentos.application.usecase.AsignarJefeUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class AsignarJefeService implements AsignarJefeUseCase {

    private final DepartamentoRepositoryPort repositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    @Override
    public Departamento ejecutar(AsignarJefeCommand command) {


        Usuario usuario = usuarioRepositoryPort.buscarPorId(command.idJefe()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Roles rolJefe = Roles.JEFE_DE_DEPARTAMENTO;

        if (!usuario.getRoles().contains(rolJefe)) {
            throw new IllegalArgumentException("El usuario no tiene el rol de Jefe de Departamento");
        }

        Departamento departamento = repositoryPort.buscarPorId(command.id());

        departamento.asignarJefe(command.idJefe());

        return repositoryPort.guardar(departamento);
    }

}


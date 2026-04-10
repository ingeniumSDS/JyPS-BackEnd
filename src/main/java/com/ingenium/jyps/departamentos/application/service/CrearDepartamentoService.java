package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearDepartamentoService implements CrearDepartamentoUseCase {

    private final DepartamentoRepositoryPort repositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;


    @Override
    public Departamento ejecutar(CrearDepartamentoCommand command) {

        if (repositoryPort.buscarPorNombre(command.nombre().trim().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un departamento con ese nombre.");
        }

        if (repositoryPort.buscarPorJefeId(command.jefeId()).isPresent()) {
            throw new IllegalArgumentException("El jefe ya está asignado a otro departamento.");
        }


        if (command.jefeId() > 0) {

            Usuario usuario = usuarioRepositoryPort.buscarPorId(command.id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Roles rolJefe = Roles.JEFE_DE_DEPARTAMENTO;

            if (!usuario.getRoles().contains(rolJefe)) {
                throw new RuntimeException("El usuario no tiene el rol de Jefe de Departamento");
            }

        }

        Departamento departamento = new Departamento(
                command.nombre().trim().toUpperCase(),
                command.descripcion(),
                command.jefeId()
        );

        // AQUÍ: Capturamos y retornamos el objeto que ya trae el ID de la base de datos
        return repositoryPort.guardar(departamento);
    }


}

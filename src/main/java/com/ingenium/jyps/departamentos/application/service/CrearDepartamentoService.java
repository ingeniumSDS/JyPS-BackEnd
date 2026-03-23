package com.ingenium.jyps.departamentos.application.service;

import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CrearDepartamentoService implements CrearDepartamentoUseCase {

    private final DepartamentoRepositoryPort repositoryPort;

    // Inyección de dependencias por constructor
    public CrearDepartamentoService(DepartamentoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Departamento ejecutar(CrearDepartamentoCommand command) {
        // 1. Validar regla de negocio cruzada (Ej. que el nombre no se repita)
        if (repositoryPort.findByNombre(command.nombre().trim().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un departamento con ese nombre.");
        }

        // 2. Crear la entidad de Dominio (usando tu constructor 1)
        Departamento nuevoDepartamento = new Departamento(
                command.nombre(),
                command.descripcion(),
                command.jefeId()
        );

        // AQUÍ: Capturamos y retornamos el objeto que ya trae el ID de la base de datos
        return repositoryPort.save(nuevoDepartamento);
    }
}

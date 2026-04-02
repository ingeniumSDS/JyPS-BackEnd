package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.usecase.ListarDepartamentosUseCase;
import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request.CrearDepartamentoRequest;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response.DepartamentoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/departamentos")
@Tag(name = "Departamentos", description = "Operaciones relacionadas con la gestión de departamentos")
public class DepartamentoController {

    private final CrearDepartamentoUseCase crearDepartamentoUseCase;
    private final ListarDepartamentosUseCase listarDepartamentosUseCase;

    public DepartamentoController(CrearDepartamentoUseCase crearDepartamentoUseCase, ListarDepartamentosUseCase listarDepartamentosUseCase) {
        this.crearDepartamentoUseCase = crearDepartamentoUseCase;
        this.listarDepartamentosUseCase = listarDepartamentosUseCase;
    }
    @Operation(summary = "Crear un nuevo departamento", description = "Crea un nuevo departamento con la información proporcionada (Ej. Nombre, descripción y jefe) y devuelve los datos del departamento registrado junto con la ubicación del recurso creado")
    @PostMapping("")
    public ResponseEntity<DepartamentoResponse> crear(@RequestBody CrearDepartamentoRequest request) {

        CrearDepartamentoCommand command = new CrearDepartamentoCommand(
                request.nombre(),
                request.descripcion(),
                request.jefeId()
        );

        Departamento nuevoDepartamento = crearDepartamentoUseCase.ejecutar(command);

        DepartamentoResponse response = DepartamentoResponse.desdeDominio(nuevoDepartamento);

        URI location = URI.create("/api/v1/usuarios/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("")
    @Operation(summary = "Listar departamentos", description = "Obtiene una lista de todos los departamentos registrados en el sistema, incluyendo su nombre, descripción y jefe")
    public ResponseEntity<List<DepartamentoResponse>> findAll() {

        List<Departamento> departamentos = listarDepartamentosUseCase.ejecutar();

        List<DepartamentoResponse> response = departamentos
                .stream()
                .map(DepartamentoResponse::desdeDominio)
                .toList();

        return ResponseEntity.ok(response);
    }
}

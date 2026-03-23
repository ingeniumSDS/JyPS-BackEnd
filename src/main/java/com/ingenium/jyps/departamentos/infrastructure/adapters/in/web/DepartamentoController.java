package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.usecase.ListarDepartamentosUseCase;
import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request.CrearDepartamentoRequest;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response.DepartamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/departamentos")
@CrossOrigin("*")
public class DepartamentoController {

    private final CrearDepartamentoUseCase crearDepartamentoUseCase;
    private final ListarDepartamentosUseCase listarDepartamentosUseCase;

    public DepartamentoController(CrearDepartamentoUseCase crearDepartamentoUseCase, ListarDepartamentosUseCase listarDepartamentosUseCase) {
        this.crearDepartamentoUseCase = crearDepartamentoUseCase;
        this.listarDepartamentosUseCase = listarDepartamentosUseCase;
    }

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
    public ResponseEntity<List<DepartamentoResponse>> findAll() {

        List<Departamento> departamentos = listarDepartamentosUseCase.ejecutar();

        List<DepartamentoResponse> response = departamentos
                .stream()
                .map(DepartamentoResponse::desdeDominio)
                .toList();

        return ResponseEntity.ok(response);
    }
}

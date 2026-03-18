package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.ports.in.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.ports.in.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/departamentos")
public class DepartamentoController {

    private final CrearDepartamentoUseCase crearDepartamentoUseCase;

    public DepartamentoController(CrearDepartamentoUseCase crearDepartamentoUseCase) {
        this.crearDepartamentoUseCase = crearDepartamentoUseCase;
    }

    @PostMapping("")
    public ResponseEntity<Void> crear(@RequestBody CrearDepartamentoCommand command) {
        Departamento nuevoDepartamento = crearDepartamentoUseCase.ejecutar(command);

        URI location = URI.create("/api/v1/departamentos/" + nuevoDepartamento.getId());

        return ResponseEntity.created(location).build();
    }

}

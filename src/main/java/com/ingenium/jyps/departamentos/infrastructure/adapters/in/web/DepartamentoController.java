package com.ingenium.jyps.departamentos.infrastructure.adapters.in.web;

import com.ingenium.jyps.departamentos.application.ports.in.command.AsignarJefeCommand;
import com.ingenium.jyps.departamentos.application.usecase.AsignarJefeUseCase;
import com.ingenium.jyps.departamentos.application.usecase.ListarDepartamentosUseCase;
import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.usecase.CrearDepartamentoUseCase;
import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request.CrearDepartamentoRequest;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.response.DepartamentoResponse;
import com.ingenium.jyps.users.application.ports.in.usecases.ConsultarUsuariosUseCase;
import com.ingenium.jyps.users.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/departamentos")
@Tag(name = "Departamentos", description = "Operaciones relacionadas con la gestión de departamentos")
public class DepartamentoController {

    private final CrearDepartamentoUseCase crearDepartamentoUseCase;
    private final ListarDepartamentosUseCase listarDepartamentosUseCase;
    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;
    private final AsignarJefeUseCase asignarJefeUseCase;
    private final ConsultarUsuariosUseCase contarEmpleadosUseCase;

    @Operation(summary = "Crear un nuevo departamento", description = "Crea un nuevo departamento con la información proporcionada (Ej. Nombre, descripción y jefe) y devuelve los datos del departamento registrado junto con la ubicación del recurso creado")
    @PostMapping("")
    public ResponseEntity<DepartamentoResponse> crear(@RequestBody CrearDepartamentoRequest request) {


        CrearDepartamentoCommand command = new CrearDepartamentoCommand(
                request.nombre(),
                request.descripcion(),
                request.jefeId().orElse(null)
        );

        Departamento nuevoDepartamento = crearDepartamentoUseCase.ejecutar(command);
        String nombreJefe = consultarUsuariosUseCase.obtenerPorId(nuevoDepartamento.getJefeId())
                .map(Usuario::nombreCompleto)
                .orElse("Sin jefe asignado");


        DepartamentoResponse response = DepartamentoResponse.desdeDominio(nuevoDepartamento, nombreJefe, 0L);

        URI location = URI.create("/api/v1/usuarios/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("")
    @Operation(summary = "Listar departamentos", description = "Obtiene una lista de todos los departamentos registrados en el sistema, incluyendo su nombre, descripción y jefe")
    public ResponseEntity<List<DepartamentoResponse>> findAll() {

        List<Departamento> departamentos = listarDepartamentosUseCase.ejecutar();

        List<DepartamentoResponse> response = departamentos.stream()
                .map(departamento -> {
                    String nombreJefe = consultarUsuariosUseCase.obtenerPorId(departamento.getJefeId())
                            .map(Usuario::nombreCompleto)
                            .orElse("Sin jefe asignado");
                    Long totalEmpleados = contarEmpleadosUseCase.contarPorDepartamento(departamento.getId());
                    return DepartamentoResponse.desdeDominio(departamento, nombreJefe, totalEmpleados);
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/asignar-jefe")
    @Operation(summary = "Asignar jefe a departamento", description = "Asigna un jefe a un departamento existente, lo que activa el departamento si no estaba activo previamente")
    public ResponseEntity<DepartamentoResponse> asignarJefe(@PathVariable Long id, @RequestParam Long jefeId) {
        AsignarJefeCommand command = new AsignarJefeCommand(id, jefeId);
        Departamento departamentoAsignado =  asignarJefeUseCase.ejecutar(command);
        String jefe = consultarUsuariosUseCase.obtenerPorId(jefeId)
                .map(Usuario::nombreCompleto)
                .orElse("Sin jefe asignado");
        Long totalEmpleados = contarEmpleadosUseCase.contarPorDepartamento(id);
        DepartamentoResponse.desdeDominio(departamentoAsignado, jefe, totalEmpleados);
        return ResponseEntity.ok(DepartamentoResponse.desdeDominio(departamentoAsignado, jefe, totalEmpleados));
    }
}

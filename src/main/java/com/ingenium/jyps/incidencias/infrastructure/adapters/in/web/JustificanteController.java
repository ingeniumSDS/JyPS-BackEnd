package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.*;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RangoDeFechasRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/justificantes")
@EnableWebSecurity
@Tag(name = "4 - Justificantes", description = "Operaciones relacionadas con la gestión de justificantes.")
public class JustificanteController {

    private final SolicitarJustificanteUseCase solicitarJustificanteUseCase;
    private final JustificanteMapper justificanteMapper;
    private final RevisarJustificanteUseCase revisarJustificanteUseCase;
    private final JustificantesPorEmpleadoUseCase obtenerJustificantesPorEmpleado;
    private final JustificantesPorJefeUseCase obtenerJustificantesPorJefe;
    private final DetallesJustificanteUseCase detallesJustificanteUseCase;
    private final BuscarJustificantePorRangoDeFechas buscarJustificantePorRangoDeFechas;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Nuevo Justificante", description = "Permite a un empleado solicitar un nuevo justificante, adjuntando archivos relacionados.")
    public ResponseEntity<JustificanteResponse> solicitarJustificante(

            @RequestPart("data") SolicitarJustificanteRequest request,
            @RequestPart("archivos") List<MultipartFile> archivos) {

        SolicitarJustificanteCommand command = justificanteMapper.toCommand(request, archivos);


        Justificante nuevoJustificante = solicitarJustificanteUseCase
                .ejecutar(
                        command
                );


        URI location = URI.create("/api/v1/justificantes/" + nuevoJustificante.getId());

        JustificanteResponse justificanteResponse = justificanteMapper.toResponse(nuevoJustificante);

        return ResponseEntity.created(location).body(justificanteResponse);
    }

    @PutMapping("/revisar")
    @Operation(summary = "Revisar Justificante", description = "Permite a un jefe revisar un justificante pendiente, aprobándolo o rechazándolo con una observación.")
    public ResponseEntity<JustificanteResponse> revisarJustificante(
            @Valid @RequestBody RevisarJustificanteRequest request) {
        RevisarJustificanteCommand command = justificanteMapper.toRevisarJustificanteCommand(request);
        return ResponseEntity.ok(justificanteMapper.toResponse(revisarJustificanteUseCase.ejecutar(command)));
    }

    @GetMapping("/empleado")
    @Operation(summary = "Justificantes por Empleado", description = "Obtiene la lista de justificantes asociados a un empleado específico.")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorEmpleado(@RequestParam Long empleadoId) {
        List<Justificante> justificantes = obtenerJustificantesPorEmpleado.ejecutar(empleadoId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/jefe")
    @Operation(summary = "Justificantes por Jefe", description = "Obtiene la lista de justificantes asociados a un jefe específico.")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorJefe(@RequestParam Long jefeId) {
        List<Justificante> justificantes = obtenerJustificantesPorJefe.ejecutar(jefeId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/detalles")
    @Operation(summary = "Detalles del Justificante", description = "Muestra los detalles del pase del justificante.")
    public ResponseEntity<JustificanteResponse> obtenerDetallesJustificante(@PathVariable Long id) {
        Justificante justificante = detallesJustificanteUseCase.ejecutar(id);
        JustificanteResponse response = justificanteMapper.toResponse(justificante);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/rango-fechas")
    @Operation(summary = "Justificantes de por Rango de Fechas", description = "Obtiene la lista de justificantes dentro de un rango de fechas específico y con estado A_TIEMPO o RETARDO")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorRangoDeFechas(
            @RequestBody @Valid RangoDeFechasRequest request) {
        RangoDeFechasCommand command = justificanteMapper.toRangoDeFechasCommand(request);
        List<Justificante> justificantes = buscarJustificantePorRangoDeFechas.ejecutar(command);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

}

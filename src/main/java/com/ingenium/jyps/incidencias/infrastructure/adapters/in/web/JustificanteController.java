package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.*;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/justificantes")
@CrossOrigin("*")
@Tag(name = "4 - Justificantes", description = "Operaciones relacionadas con la gestión de justificantes.")
public class JustificanteController {

    private final SolicitarJustificanteUseCase solicitarJustificanteUseCase;
    private final JustificanteMapper justificanteMapper;
    private final RevisarJustificanteUseCase revisarJustificanteUseCase;
    private final JustificantesPorEmpleadoUseCase obtenerJustificantesPorEmpleado;
    private final JustificantesPorJefeUseCase obtenerJustificantesPorJefe;
    private final DetallesJustificanteUseCase detallesJustificanteUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<JustificanteResponse> revisarJustificante(
            @RequestBody RevisarJustificanteRequest request) {
        RevisarJustificanteCommand command = justificanteMapper.toRevisarJustificanteCommand(request);
        return ResponseEntity.ok(justificanteMapper.toResponse(revisarJustificanteUseCase.ejecutar(command)));
    }

    @GetMapping("/empleado")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorEmpleado(@RequestParam Long empleadoId) {
        List<Justificante> justificantes = obtenerJustificantesPorEmpleado.ejecutar(empleadoId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/jefe")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorJefe(@RequestParam Long jefeId) {
        List<Justificante> justificantes = obtenerJustificantesPorJefe.ejecutar(jefeId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<JustificanteResponse> obtenerDetallesJustificante(@PathVariable Long id) {
        Justificante justificante = detallesJustificanteUseCase.ejecutar(id);
        JustificanteResponse response = justificanteMapper.toResponse(justificante);
        return ResponseEntity.ok(response);
    }

}

package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.RevisarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.PasesPorEmpleadoUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.PasesPorJefeUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.RevisarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.SolicitarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.PaseDeSalidaResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.PaseDeSalidaMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pases")
@CrossOrigin("*")
@Tag(name = "5 - Pases de Salida", description = "Operaciones relacionadas con la gestión de pases de salida.")
public class PaseDeSalidaController {

    private final SolicitarPaseDeSalidaUseCase paseDeSalidaUseCase;
    private final RevisarPaseDeSalidaUseCase revisarPaseDeSalida;
    private final PasesPorEmpleadoUseCase obtenerPasesPorEmpleado;
    private final PasesPorJefeUseCase obtenerPasesPorJefe;
    private final PaseDeSalidaMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaseDeSalidaResponse> solicitar(
            @RequestPart("data") SolicitarPaseDeSalidaRequest request,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {

        List<MultipartFile> archivosSeguros = (archivos != null) ? archivos : Collections.emptyList();

        PaseDeSalida nuevoPaseDeSalida =
                paseDeSalidaUseCase.ejecutar(
                        mapper.toCommand(request, archivosSeguros)
                );



        URI location = URI.create("/api/v1/justificantes/" + nuevoPaseDeSalida.getId());

        PaseDeSalidaResponse paseDeSalidaResponse = mapper.toResponse(nuevoPaseDeSalida);

        return ResponseEntity.created(location).body(paseDeSalidaResponse);
    }

    @PutMapping("/revisar")
    public ResponseEntity<PaseDeSalidaResponse> revisarPaseDeSalida(
            @RequestBody RevisarPaseDeSalidaRequest request) {
        RevisarPaseDeSalidaCommand command = mapper.toRevisarPaseDeSalidaCommand(request);
        return ResponseEntity.ok(mapper.toResponse(revisarPaseDeSalida.ejecutar(command)));
    }

    @GetMapping("/empleado")
    public ResponseEntity<List<PaseDeSalidaResponse>> obtenerPasesPorEmpleado(@RequestParam Long empleadoId) {
        List<PaseDeSalida> pases = obtenerPasesPorEmpleado.ejecutar(empleadoId);
        List<PaseDeSalidaResponse> responses = pases.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/jefe")
    public ResponseEntity<List<PaseDeSalidaResponse>> obtenerPasesPorJefe(@RequestParam Long jefeId) {
        List<PaseDeSalida> pases = obtenerPasesPorJefe.ejecutar(jefeId);
        List<PaseDeSalidaResponse> responses = pases.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

}

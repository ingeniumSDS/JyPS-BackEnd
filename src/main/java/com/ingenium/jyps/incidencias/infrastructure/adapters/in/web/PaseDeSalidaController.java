package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.RevisarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.*;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RangoDeFechasRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.PaseDeSalidaResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.PaseDeSalidaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pases")
@EnableWebSecurity
@Tag(name = "5 - Pases de Salida", description = "Operaciones relacionadas con la gestión de pases de salida.")
public class PaseDeSalidaController {

    private final SolicitarPaseDeSalidaUseCase paseDeSalidaUseCase;
    private final RevisarPaseDeSalidaUseCase revisarPaseDeSalida;
    private final PasesPorEmpleadoUseCase obtenerPasesPorEmpleado;
    private final PasesPorJefeUseCase obtenerPasesPorJefe;
    private final DetallesPaseUseCase detallesPaseUseCase;
    private final BuscarPasePorRangoDeFechas buscarPasePorRangoDeFechas;
    private final CheckUseCase checkUseCase;
    private final PaseDeSalidaMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Nuevo Pase de Salida", description = "Permite a un empleado solicitar un nuevo Pase de Salida, adjuntando archivos relacionados.")
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
    @Operation(summary = "Revisar Pase de Salida", description = "Permite a un jefe revisar un pase de salida pendiente, " +
            "aprobándolo (estado = APROBADO) o rechazándolo (estado = RECHAZADO) con una observación (mensaje).")
    public ResponseEntity<PaseDeSalidaResponse> revisarPaseDeSalida(
            @Valid @RequestBody RevisarPaseDeSalidaRequest request) {
        RevisarPaseDeSalidaCommand command = mapper.toRevisarPaseDeSalidaCommand(request);
        return ResponseEntity.ok(mapper.toResponse(revisarPaseDeSalida.ejecutar(command)));
    }

    @GetMapping("/empleado")
    @Operation(summary = "Pases de Salida por Empleado", description = "Obtiene la lista de pases de salida asociados a un empleado específico.")
    public ResponseEntity<List<PaseDeSalidaResponse>> obtenerPasesPorEmpleado(@RequestParam Long empleadoId) {
        List<PaseDeSalida> pases = obtenerPasesPorEmpleado.ejecutar(empleadoId);
        List<PaseDeSalidaResponse> responses = pases.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/jefe")
    @Operation(summary = "¨Pases por Jefe", description = "Obtiene la lista de pases de salida asociados a un jefe específico.")
    public ResponseEntity<List<PaseDeSalidaResponse>> obtenerPasesPorJefe(@RequestParam Long jefeId) {
        List<PaseDeSalida> pases = obtenerPasesPorJefe.ejecutar(jefeId);
        List<PaseDeSalidaResponse> responses = pases.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/detalles")
    @Operation(summary = "Detalles del Pase de Salida", description = "Muestra los detalles del pase del pase de salida.")
    public ResponseEntity<PaseDeSalidaResponse> obtenerDetallesJustificante(@PathVariable Long id) {
        PaseDeSalida paseDeSalida = detallesPaseUseCase.ejecutar(id);
        PaseDeSalidaResponse response = mapper.toResponse(paseDeSalida);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{qr}")
    @Operation(summary = "Check IN/OUT del Pase de Salida", description = "Permite validar un pase de salida escaneando su código QR, actualizando su estado a 'FUERA' o 'A_TIEMPO'")
    public ResponseEntity<PaseDeSalidaResponse> check(
            @PathVariable String qr
    ) {
        PaseDeSalida paseDeSalida = checkUseCase.ejecutar(qr);
        PaseDeSalidaResponse response = mapper.toResponse(paseDeSalida);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rango-fechas")
    @Operation(summary = "Pases de Salida por Rango de Fechas", description = "Obtiene la lista de justificantes dentro de un rango de fechas específico y con estado A_TIEMPO o RETARDO")
    public ResponseEntity<List<PaseDeSalidaResponse>> obtenerJustificantesPorRangoDeFechas(
            @RequestBody @Valid RangoDeFechasRequest request) {
        RangoDeFechasCommand command = mapper.toRangoDeFechasCommand(request);
        List<PaseDeSalida> pasesDeSalida = buscarPasePorRangoDeFechas.ejecutar(command);
        List<PaseDeSalidaResponse> responses = pasesDeSalida.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }


}

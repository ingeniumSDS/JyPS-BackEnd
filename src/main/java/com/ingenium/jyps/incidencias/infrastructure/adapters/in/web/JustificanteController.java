package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.*;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.out.StoragePort;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RangoDeFechasRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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
    private final EliminarIncidenciaPendiente eliminarJustificanteUseCase;

    private final StoragePort storagePort;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('EMPLEADO')")
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
    @PreAuthorize("hasRole('JEFE_DE_DEPARTAMENTO')") // Solo el jefe puede acceder a esta ruta
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Revisar Justificante", description = "Permite a un jefe revisar un justificante pendiente, aprobándolo o rechazándolo con una observación.")
    public ResponseEntity<JustificanteResponse> revisarJustificante(
            @Valid @RequestBody RevisarJustificanteRequest request) {
        RevisarJustificanteCommand command = justificanteMapper.toRevisarJustificanteCommand(request);
        return ResponseEntity.ok(justificanteMapper.toResponse(revisarJustificanteUseCase.ejecutar(command)));
    }

    @PreAuthorize("hasAnyRole('EMPLEADO', 'JEFE_DE_DEPARTAMENTO')") // Solo el empleado puede acceder a esta ruta
    @GetMapping("/empleado")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Justificantes por Empleado", description = "Obtiene la lista de justificantes asociados a un empleado específico.")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorEmpleado(@RequestParam Long empleadoId) {
        List<Justificante> justificantes = obtenerJustificantesPorEmpleado.ejecutar(empleadoId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/jefe")
    @PreAuthorize("hasRole('JEFE_DE_DEPARTAMENTO')") // Solo el jefe puede acceder a esta ruta
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Justificantes por Jefe", description = "Obtiene la lista de justificantes asociados a un jefe específico.")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorJefe(@RequestParam Long jefeId) {
        List<Justificante> justificantes = obtenerJustificantesPorJefe.ejecutar(jefeId);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/detalles")
    @PreAuthorize("hasAnyRole('AUDITOR', 'JEFE_DE_DEPARTAMENTO', 'EMPLEADO')") // Permite acceso a múltiples roles
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Detalles del Justificante", description = "Muestra los detalles del pase del justificante.")
    public ResponseEntity<JustificanteResponse> obtenerDetallesJustificante(@PathVariable Long id) {
        Justificante justificante = detallesJustificanteUseCase.ejecutar(id);
        JustificanteResponse response = justificanteMapper.toResponse(justificante);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/rango-fechas")
    @Operation(summary = "Justificantes por Rango de Fechas", description = "Obtiene la lista de justificantes dentro de un rango de fechas específico y con estado A_TIEMPO o RETARDO")
    public ResponseEntity<List<JustificanteResponse>> obtenerJustificantesPorRangoDeFechas(
            @RequestBody @Valid RangoDeFechasRequest request) {
        RangoDeFechasCommand command = justificanteMapper.toRangoDeFechasCommand(request);
        List<Justificante> justificantes = buscarJustificantePorRangoDeFechas.ejecutar(command);
        List<JustificanteResponse> responses = justificantes.stream()
                .map(justificanteMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{empleadoId}/{nombreArchivo}")
    @PreAuthorize("hasAnyRole('AUDITOR', 'JEFE_DE_DEPARTAMENTO', 'EMPLEADO')") // Permite acceso a múltiples roles
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Descargar Archivo Adjunto", description = "Permite descargar un archivo adjunto asociado a un justificante.")
    public ResponseEntity<Resource> descargarArchivo(
            @PathVariable Long empleadoId,
            @PathVariable String nombreArchivo) {


        // Obtenemos el archivo desde el storage (necesitarás un método en el port que devuelva Resource o bytes)
        byte[] contenido = storagePort.leerArchivo(empleadoId, nombreArchivo);
        Resource resource = new ByteArrayResource(contenido);

        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = switch (extension) {
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };

        return ResponseEntity.ok()
                .contentType(mediaType) // En lugar de forzar siempre OCTET_STREAM
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreArchivo + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elminar Justificante", description = "Permite eliminar un justificante específico.")
    @PreAuthorize("hasRole('EMPLEADO')") // Solo el empleado puede eliminar su justificante
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> eliminarJustificante(@PathVariable Long id) {
        eliminarJustificanteUseCase.eliminarJustificante(id);
        return ResponseEntity.noContent().build();
    }
}

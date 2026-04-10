package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
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
}

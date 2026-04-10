package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
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

}

package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.PaseDeSalidaResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.PaseDeSalidaMapper;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pases")
public class PaseDeSalidaController {

    private final SolicitarPaseDeSalidaUseCase paseDeSalidaUseCase;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PaseDeSalidaMapper mapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaseDeSalidaResponse> solicitar(
            @RequestPart("data") SolicitarPaseDeSalidaRequest request,
            @RequestPart("archivos") List<MultipartFile> archivos) {

        PaseDeSalida nuevoPaseDeSalida =
                paseDeSalidaUseCase.ejecutar(
                        mapper.toCommand(request, archivos)
                );



        URI location = URI.create("/api/v1/justificantes/" + nuevoPaseDeSalida.getId());

        PaseDeSalidaResponse paseDeSalidaResponse = mapper.toResponse(nuevoPaseDeSalida);

        return ResponseEntity.created(location).body(paseDeSalidaResponse);
    }
}

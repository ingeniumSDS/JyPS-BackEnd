package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.JustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/justificantes")
public class JustificanteController {

    private final SolicitarJustificanteUseCase solicitarJustificanteUseCase;
    private final JustificanteMapper justificanteMapper;

    @PostMapping("")
    public ResponseEntity<JustificanteResponse> solicitarJustificante(@RequestBody JustificanteRequest request) {

        Justificante nuevoJustificante = solicitarJustificanteUseCase
                .ejecutar(
                        justificanteMapper.toCommand(request)
                );

        URI location = URI.create("/api/v1/justificantes/" + nuevoJustificante.getId());

        JustificanteResponse justificanteResponse = justificanteMapper.toResponse(nuevoJustificante);

        return ResponseEntity.created(location).body(justificanteResponse);
    }

}

package com.ingenium.jyps.incidencias.infrastructure.adapters.in.web;


import com.ingenium.jyps.incidencias.application.ports.in.usecases.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.JustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/justificantes")
@RequiredArgsConstructor
public class JustificanteController {

    private final SolicitarJustificanteUseCase solicitarJustificanteUseCase;


    @PostMapping("")
    public JustificanteResponse generarJustificante(@RequestBody JustificanteRequest request) {

        SolicitarJustificanteCommand command = new SolicitarJustificanteCommand(
                request.idEmpleado(),
                request.idJefe(),
                request.fechaSolicitada(),
                request.descripcion(),
                request.archivos()
        );

        Justificante justificante = solicitarJustificanteUseCase.ejecutar(command);

        return new JustificanteResponse(
                justificante.getId(),
                justificante.getEmpleadoId(),
                justificante.getJefeId(),
                justificante.getFechaSolicitada(),
                justificante.getFechaSolicitud(),
                justificante.getDescripcion(),
                justificante.getArchivos(),
                justificante.getEstado()
        );

    }
}

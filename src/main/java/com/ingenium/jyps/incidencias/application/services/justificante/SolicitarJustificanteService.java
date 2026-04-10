package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.out.StoragePort;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.SolicitarJustificanteUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitarJustificanteService implements SolicitarJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;
    private final StoragePort storagePort;

    @Override
    public Justificante ejecutar(SolicitarJustificanteCommand command) {

        List<String> archivosGuardados = storagePort.guardarArchivos(command.empleadoId(), command.archivos());

        Justificante nuevoJustificante = new Justificante(
                command.empleadoId(),
                command.jefeId(),
                command.fechaSolicitada(),
                command.descripcion(),
                archivosGuardados
        );


        return  justificanteRepositoryPort.solicitar(nuevoJustificante);

    }
}

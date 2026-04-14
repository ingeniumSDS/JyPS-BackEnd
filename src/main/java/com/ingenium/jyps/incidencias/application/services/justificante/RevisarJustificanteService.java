package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.RevisarJustificanteUseCase;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RevisarJustificanteService implements RevisarJustificanteUseCase {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public Justificante ejecutar(RevisarJustificanteCommand command) {
        Justificante justificante = justificanteRepositoryPort.buscarPorId(command.justificanteId());
        justificante.revisar(command.estado(), command.comentario());
        return justificanteRepositoryPort.guardar(justificante);
    }
}

package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.RevisarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.RevisarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisarPaseDeSalidaService implements RevisarPaseDeSalidaUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public PaseDeSalida ejecutar(RevisarPaseDeSalidaCommand command) {
        PaseDeSalida paseDeSalida = paseDeSalidaRepositoryPort.buscarPorId(command.paseDeSalidaId());
        paseDeSalida.revisar(command.estado(), command.comentario());
        return paseDeSalidaRepositoryPort.solicitar(paseDeSalida);
    }
}

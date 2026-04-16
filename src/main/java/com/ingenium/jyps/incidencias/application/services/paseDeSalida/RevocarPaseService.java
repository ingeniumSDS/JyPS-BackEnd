package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.RevocarPaseUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevocarPaseService implements RevocarPaseUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public String ejecutar(Long paseId) {
        PaseDeSalida pase = paseDeSalidaRepositoryPort.buscarPorId(paseId);

        pase.revocar();

        paseDeSalidaRepositoryPort.solicitar(pase);

        return "Pase de salida revocado exitosamente.";
    }
}

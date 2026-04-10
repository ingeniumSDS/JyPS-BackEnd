package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.DetallesPaseUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetallesPaseService implements DetallesPaseUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public PaseDeSalida ejecutar(Long id) {
        return paseDeSalidaRepositoryPort.buscarPorId(id);
    }
}

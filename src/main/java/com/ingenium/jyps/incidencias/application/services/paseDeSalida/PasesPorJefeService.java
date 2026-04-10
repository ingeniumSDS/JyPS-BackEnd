package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.PasesPorJefeUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasesPorJefeService implements PasesPorJefeUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public List<PaseDeSalida> ejecutar(Long jefeId) {
        return paseDeSalidaRepositoryPort.buscarPorIdJefe(jefeId);
    }
}

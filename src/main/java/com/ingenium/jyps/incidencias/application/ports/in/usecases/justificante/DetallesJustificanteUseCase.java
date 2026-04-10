package com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante;

import com.ingenium.jyps.incidencias.domain.model.Justificante;

public interface DetallesJustificanteUseCase {
    Justificante ejecutar(Long id);
}

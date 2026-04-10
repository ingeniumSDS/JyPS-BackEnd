package com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

public interface DetallesPaseUseCase {
    PaseDeSalida ejecutar(Long id);
}

package com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

import java.util.List;

public interface PasesPorJefeUseCase {
    List<PaseDeSalida> ejecutar(Long jefeId);

}

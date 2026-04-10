package com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante;

import com.ingenium.jyps.incidencias.domain.model.Justificante;

import java.util.List;

public interface JustificantesPorJefeUseCase {
    List<Justificante> ejecutar(Long usuarioId);
}

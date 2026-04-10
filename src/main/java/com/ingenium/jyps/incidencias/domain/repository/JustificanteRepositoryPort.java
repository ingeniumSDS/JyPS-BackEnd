package com.ingenium.jyps.incidencias.domain.repository;

import com.ingenium.jyps.incidencias.domain.model.Justificante;

import java.util.Optional;

public interface JustificanteRepositoryPort {

    Justificante solicitar(Justificante justificante);

    Justificante buscarPorId(long justificanteId);



}

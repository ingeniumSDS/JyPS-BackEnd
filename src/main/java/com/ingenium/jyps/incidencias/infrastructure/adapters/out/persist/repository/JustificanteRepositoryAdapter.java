package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.application.out.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;

public class JustificanteRepositoryAdapter implements JustificanteRepositoryPort {

    private JpaJustificanteRepositoy jpaJustificanteRepositoy;


    @Override
    public Justificante solicitar(Justificante justificante) {
        JustificanteEntity justificanteEntity = new JustificanteEntity();
        return null;
    }
}

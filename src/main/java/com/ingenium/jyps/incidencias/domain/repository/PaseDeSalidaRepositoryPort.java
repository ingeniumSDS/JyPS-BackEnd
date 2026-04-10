package com.ingenium.jyps.incidencias.domain.repository;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;


public interface PaseDeSalidaRepositoryPort {

    PaseDeSalida solicitar(PaseDeSalida paseDeSalida);

    boolean solicitudEnCurso();

    PaseDeSalida buscarPorId(long paseDeSalidaId);

}

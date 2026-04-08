package com.ingenium.jyps.incidencias.domain.repository;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PaseDeSalidaRepositoryPort {

    PaseDeSalida solicitar(PaseDeSalida paseDeSalida);

    boolean solicitudEnCurso();

}

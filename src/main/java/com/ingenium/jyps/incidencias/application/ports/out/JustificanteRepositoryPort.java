package com.ingenium.jyps.incidencias.application.ports.out;

import com.ingenium.jyps.incidencias.domain.model.Justificante;

import java.time.LocalDate;
import java.util.Optional;

public interface JustificanteRepositoryPort {


    Justificante solicitar(Justificante justificante);

    Optional<Justificante> recuperar(LocalDate localDate, Long idEmpleado);


    boolean existePorFechaSolicitada(LocalDate localDate, Long idEmpleado);
}

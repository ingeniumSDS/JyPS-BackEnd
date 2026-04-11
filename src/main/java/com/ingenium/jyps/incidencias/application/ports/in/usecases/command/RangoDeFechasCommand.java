package com.ingenium.jyps.incidencias.application.ports.in.usecases.command;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record RangoDeFechasCommand(
        LocalDate fechaInicio,
        LocalDate fechaFin
) {



}

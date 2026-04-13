package com.ingenium.jyps.incidencias.domain.repository;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;

import java.util.List;

public interface JustificanteRepositoryPort {

    Justificante guardar(Justificante justificante);

    Justificante buscarPorId(long justificanteId);

    List<Justificante> buscarPorEmpleado(Long usuarioId);

    List<Justificante> buscarPorJefe(Long usuarioId);

    List<Justificante> buscarPorRangoDeFechas(RangoDeFechasCommand command);

    void borrar(Long idPaseDeSalida);
}

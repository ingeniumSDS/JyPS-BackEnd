package com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

import java.util.List;

public interface BuscarPasePorRangoDeFechas {
    List<PaseDeSalida> ejecutar(RangoDeFechasCommand command);

}

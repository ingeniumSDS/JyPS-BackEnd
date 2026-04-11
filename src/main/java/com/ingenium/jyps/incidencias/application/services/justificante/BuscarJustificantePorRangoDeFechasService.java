package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.BuscarJustificantePorRangoDeFechas;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarJustificantePorRangoDeFechasService implements BuscarJustificantePorRangoDeFechas {

    private final JustificanteRepositoryPort justificanteRepositoryPort;

    @Override
    public List<Justificante> ejecutar(RangoDeFechasCommand command) {
        return justificanteRepositoryPort.buscarPorRangoDeFechas(command);
    }
}

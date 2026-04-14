package com.ingenium.jyps.incidencias.application.services.justificante;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.justificante.EliminarIncidenciaPendiente;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class EliminarIncidenciaPendienteService implements EliminarIncidenciaPendiente {

    private final JustificanteRepositoryPort justificanteRepositoryPort;
    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public void eliminarJustificante(Long idJustificante) {

        Justificante justificante = justificanteRepositoryPort.buscarPorId(idJustificante);
        if (justificante.getEstado() == EstadosIncidencia.PENDIENTE) {
            justificanteRepositoryPort.borrar(idJustificante);
        } else {
            throw new IllegalArgumentException("No se puede eliminar un Justificante que no está en estado PENDIENTE.");
        }
    }

    @Override
    public void eliminarPaseDeSalida(Long idPaseDeSalida) {

        PaseDeSalida paseDeSalida = paseDeSalidaRepositoryPort.buscarPorId(idPaseDeSalida);

        if (paseDeSalida.getEstado() == EstadosIncidencia.PENDIENTE) {
            paseDeSalidaRepositoryPort.borrar(idPaseDeSalida);
        } else {
            throw new IllegalArgumentException("No se puede eliminar un Pase de Salida que no está en estado PENDIENTE.");
        }
    }

}

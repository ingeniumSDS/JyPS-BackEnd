package com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida;

import com.ingenium.jyps.incidencias.domain.model.ArchivoAdjunto;
import com.ingenium.jyps.users.domain.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record SolicitarPaseDeSalidaCommand(

        // Dominio de la incidencia
        Long id,
        Long empleadoId,
        Long jefeId,
        String descripcion,
        String motivoRechazo,

        // Extras para el dominio del pase de salida
        Usuario empleado,
        LocalDateTime horaSolicitada,
        LocalDateTime horaEsperada,
        LocalDateTime horaSalidaReal,
        List<ArchivoAdjunto> archivos

) {
}
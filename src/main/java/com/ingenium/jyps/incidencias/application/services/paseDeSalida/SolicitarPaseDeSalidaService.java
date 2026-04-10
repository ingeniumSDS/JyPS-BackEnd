package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.SolicitarPaseDeSalidaUseCase;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.SolicitarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.application.ports.out.StoragePort;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitarPaseDeSalidaService implements SolicitarPaseDeSalidaUseCase {

    private final StoragePort storagePort;
    private final PaseDeSalidaRepositoryPort paseDeSalidaRepository;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    @Override
    public PaseDeSalida ejecutar(SolicitarPaseDeSalidaCommand command) {

        if (paseDeSalidaRepository.solicitudEnCurso()) {
            throw new IllegalArgumentException("Aún cuentas con una solicitud en curso.");
        }

        Usuario empleado = usuarioRepositoryPort.buscarPorId(command.empleadoId()).orElseThrow(() ->
                new IllegalArgumentException("Usuario inexistente.")
        );

        List<String> archivosGuardados = storagePort.guardarArchivos(command.empleadoId(), command.archivos());

        PaseDeSalida nuevoPase = new PaseDeSalida(
                command.empleadoId(),
                command.jefeId(),
                command.descripcion(),
                archivosGuardados,
                empleado,
                command.horaSolicitada()
        );

        nuevoPase.cargarEmpleado(empleado);

        return paseDeSalidaRepository.solicitar(nuevoPase);
    }
}

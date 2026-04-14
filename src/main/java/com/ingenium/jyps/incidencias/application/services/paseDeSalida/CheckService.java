package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.CheckUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = IllegalArgumentException.class)
public class CheckService implements CheckUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    @Override
    public PaseDeSalida ejecutar(String QR) {

        PaseDeSalida pase = paseDeSalidaRepositoryPort.buscarPorQR(QR);
        Usuario empleado = usuarioRepositoryPort.buscarPorId(pase.getEmpleadoId()).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        pase.cargarEmpleado(empleado);
        pase.check();

        return paseDeSalidaRepositoryPort.solicitar(pase);
    }
}

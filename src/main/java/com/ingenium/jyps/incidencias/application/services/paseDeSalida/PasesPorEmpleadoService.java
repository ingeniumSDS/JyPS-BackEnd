package com.ingenium.jyps.incidencias.application.services.paseDeSalida;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.paseDeSalida.PasesPorEmpleadoUseCase;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasesPorEmpleadoService implements PasesPorEmpleadoUseCase {

    private final PaseDeSalidaRepositoryPort paseDeSalidaRepositoryPort;

    @Override
    public List<PaseDeSalida> ejecutar(Long empleadoId) {
        return paseDeSalidaRepositoryPort.buscarPorIdEmpleado(empleadoId);
    }
}

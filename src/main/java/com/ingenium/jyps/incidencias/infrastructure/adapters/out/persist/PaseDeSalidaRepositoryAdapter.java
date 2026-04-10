package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.PaseDeSalidaMapper;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaPaseDeSalidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaseDeSalidaRepositoryAdapter implements PaseDeSalidaRepositoryPort {

    private final JpaPaseDeSalidaRepository jpaPaseDeSalidaRepository;
    private final PaseDeSalidaMapper paseDeSalidaMapper;


    @Override
    public PaseDeSalida solicitar(PaseDeSalida paseDeSalida) {
        PaseDeSalidaEntity nuevoPase = paseDeSalidaMapper.toEntity(paseDeSalida);
        PaseDeSalidaEntity paseGuardado = jpaPaseDeSalidaRepository.save(nuevoPase);
        return paseDeSalidaMapper.toDomain(paseGuardado);
    }

    @Override
    public boolean solicitudEnCurso() {
        return false;
    }

    @Override
    public PaseDeSalida buscarPorId(long paseDeSalidaId) {

            PaseDeSalidaEntity paseDeSalidaEntity = jpaPaseDeSalidaRepository.findById(paseDeSalidaId).orElseThrow(() ->
                    new IllegalArgumentException("Pase de salida inexistente.")
            );

        return paseDeSalidaMapper.toDomain(paseDeSalidaEntity);
    }
}

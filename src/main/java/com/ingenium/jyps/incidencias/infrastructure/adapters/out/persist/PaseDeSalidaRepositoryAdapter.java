package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.domain.repository.PaseDeSalidaRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.PaseDeSalidaMapper;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaPaseDeSalidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public List<PaseDeSalida> buscarPorIdEmpleado(Long empleadoId) {
        List<PaseDeSalidaEntity> paseDeSalidaEntities = jpaPaseDeSalidaRepository.findByEmpleado_Id(empleadoId);
        return paseDeSalidaEntities.stream()
                .map(paseDeSalidaMapper::toDomain)
                .toList();
    }

    @Override
    public List<PaseDeSalida> buscarPorIdJefe(Long jefeId) {
        List<PaseDeSalidaEntity> paseDeSalidaEntities = jpaPaseDeSalidaRepository.findByJefe_Id(jefeId);
        return paseDeSalidaEntities.stream()
                .map(paseDeSalidaMapper::toDomain)
                .toList();

    }
}

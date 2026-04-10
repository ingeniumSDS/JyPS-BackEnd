package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaJustificanteRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JustificanteRepositoryAdapter implements JustificanteRepositoryPort {

    private final JpaJustificanteRepositoy jpaJustificanteRepositoy;
    private final JustificanteMapper justificanteMapper;

    @Override
    public Justificante solicitar(Justificante justificante) {
        JustificanteEntity justificanteEntity = justificanteMapper.toEntity(justificante);
        JustificanteEntity justificanteGuardado = jpaJustificanteRepositoy.save(justificanteEntity);
        return justificanteMapper.toDomain(justificanteGuardado);
    }

    @Override
    public Justificante buscarPorId(long justificanteId) {
        JustificanteEntity justificanteEntity = jpaJustificanteRepositoy.findById(justificanteId).orElseThrow(() ->
                new IllegalArgumentException("Justificante inexistente.")
        );

        return justificanteMapper.toDomain(justificanteEntity);
    }

}

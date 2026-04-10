package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.repository.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaJustificanteRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class JustificanteRepositoryAdapter implements JustificanteRepositoryPort {

    private final JpaJustificanteRepositoy jpaJustificanteRepositoy;
    private final JustificanteMapper justificanteMapper;

    @Override
    public Justificante guardar(Justificante justificante) {
        JustificanteEntity justificanteEntity = justificanteMapper.toEntity(justificante);
        JustificanteEntity justificanteGuardado = jpaJustificanteRepositoy.save(justificanteEntity);
        Justificante justificanteRehidratado = justificanteMapper.toDomain(justificanteGuardado);

        // En altas, la relación empleado puede volver solo con id; preservamos el nombre ya resuelto en dominio.
        if (justificanteRehidratado.getNombreCompleto() == null && justificante.getEmpleado() != null) {
            justificanteRehidratado.cargarEmpleado(justificante.getEmpleado());
        }

        return justificanteRehidratado;
    }

    @Override
    public Justificante buscarPorId(long justificanteId) {
        JustificanteEntity justificanteEntity = jpaJustificanteRepositoy.findById(justificanteId).orElseThrow(() ->
                new IllegalArgumentException("Justificante inexistente.")
        );

        return justificanteMapper.toDomain(justificanteEntity);
    }

    @Override
    public List<Justificante> buscarPorEmpleado(Long usuarioId) {
        List<JustificanteEntity> justificantesEntity = jpaJustificanteRepositoy.findByEmpleado_Id(usuarioId);
        return justificantesEntity.stream()
                .map(justificanteMapper::toDomain)
                .toList();
    }

    @Override
    public List<Justificante> buscarPorJefe(Long usuarioId) {
        List<JustificanteEntity> justificanteEntities = jpaJustificanteRepositoy.findByJefe_Id(usuarioId);
        return justificanteEntities.stream()
                .map(justificanteMapper::toDomain)
                .toList();
    }

}

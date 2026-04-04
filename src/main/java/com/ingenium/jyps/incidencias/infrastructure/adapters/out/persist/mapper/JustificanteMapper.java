package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JustificanteMapper {

    private final UsuarioMapper usuarioMapper;


    public JustificanteEntity toEntity(Justificante justificante, Usuario u, Usuario jefe) {


        JustificanteEntity justificanteEntity = new JustificanteEntity();
        justificanteEntity.setId(justificante.getId());
        justificanteEntity.setEmpleado(usuarioMapper.toEntity(u));
        justificanteEntity.setJefe(usuarioMapper.toEntity(jefe));
        justificanteEntity.setDetalles(justificante.getDescripcion());
        justificanteEntity.setFechaSolicitada(justificante.getFechaSolicitada());
        justificanteEntity.setFechaSolicitud(justificante.getFechaSolicitud());
        justificanteEntity.setEstado(justificante.getEstado());



        return justificanteEntity;

    }

    public Justificante toDomain(JustificanteEntity justificanteEntity) {
        return new Justificante(
                justificanteEntity.getId(),
                justificanteEntity.getEmpleado() != null ? usuarioMapper.toDomain(justificanteEntity.getEmpleado()).getId() : null,
                justificanteEntity.getJefe() != null ? usuarioMapper.toDomain(justificanteEntity.getJefe()).getId() : null,
                justificanteEntity.getFechaSolicitada(),
                justificanteEntity.getFechaSolicitud(),
                justificanteEntity.getDetalles(),
                justificanteEntity.getEstado()
        );
    }

}

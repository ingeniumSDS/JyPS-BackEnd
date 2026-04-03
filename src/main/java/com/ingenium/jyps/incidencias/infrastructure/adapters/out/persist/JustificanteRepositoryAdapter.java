package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.application.ports.out.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaJustificanteRepository;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper.JustificanteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JustificanteRepositoryAdapter implements JustificanteRepositoryPort {

    private final JpaJustificanteRepository jpaJustificanteRepositoy;
    private final JustificanteMapper justificanteMapper;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    @Override
    public Optional<Justificante> recuperar(LocalDate fechaSolicitada, Long id) {
        return jpaJustificanteRepositoy.findByFechaSolicitadaAndEmpleado_Id
                (fechaSolicitada, id)
                .map(justificanteMapper::toDomain);
    }

    @Override
    public boolean existePorFechaSolicitada(LocalDate localDate, Long idEmpleado) {
        return jpaJustificanteRepositoy.findByFechaSolicitadaAndEmpleado_Id(localDate, idEmpleado).isPresent();
    }


    @Override
    public Justificante solicitar(Justificante justificante) {

        Usuario u = usuarioRepositoryPort.findById(justificante.getEmpleadoId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Departamento d = departamentoRepositoryPort.findById(u.getDepartamentoId()).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        Long idJefe = d.getJefeId();
        Usuario jefe = usuarioRepositoryPort.findById(idJefe).orElseThrow(() -> new RuntimeException("Jefe no encontrado"));

        JustificanteEntity justificanteEntity = justificanteMapper.toEntity(justificante, u, jefe, d);

        JustificanteEntity justificanteGuardado =  jpaJustificanteRepositoy.save(justificanteEntity);

        return justificanteMapper.toDomain(justificanteGuardado);
    }

}

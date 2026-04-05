package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.entity.DepartamentoEntity;
import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.repository.JpaDepartamentoRepository;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DepartamentoRepositoryAdapter implements DepartamentoRepositoryPort {

    private final JpaDepartamentoRepository departamentoRepository;

    @Override
    public Departamento guardar(Departamento departamento) {
        DepartamentoEntity d = mapToEntity(departamento);
        DepartamentoEntity entidadGuardada = departamentoRepository.save(d);
        return mapToDomain(entidadGuardada);
    }

    public List<Departamento> buscarTodos() {
        List<DepartamentoEntity> entities = departamentoRepository.findAll();
        return entities.stream().map(this::mapToDomain).toList();
    }

    public Optional<Departamento> buscarPorId(Long id) {
        return departamentoRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Departamento> buscarPorNombre(String nombre) {
        return departamentoRepository.findByNombre(nombre).map(this::mapToDomain);
    }


    private DepartamentoEntity mapToEntity(Departamento departamento) {
        UsuarioEntity jefeEntity = null;

        // JPA solo necesita una entidad con el ID para guardar la llave foránea
        if (departamento.getJefeId() != null) {
            jefeEntity = new UsuarioEntity();
            jefeEntity.setId(departamento.getJefeId());
        }

        return new DepartamentoEntity(
                departamento.getId(),
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.isActivo(),
                jefeEntity
        );
    }

    private Departamento mapToDomain(DepartamentoEntity entity) {

        Long jefeId = (entity.getJefe() != null) ? entity.getJefe().getId() : null;

        return new Departamento(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.isActivo(),
                jefeId
        );
    }

}
package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DepartamentoRepositoryAdapter implements DepartamentoRepositoryPort {

    private final SpringDataDepartamentoRepository repository;

    public DepartamentoRepositoryAdapter(SpringDataDepartamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Departamento save(Departamento departamento) {
        DepartamentoEntity d = mapToEntity(departamento);
        DepartamentoEntity entidadGuardada = repository.save(d);
        return mapToDomain(entidadGuardada);
    }

    @Override
    public Optional<Departamento> findById(Long id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Departamento> findByNombre(String nombre) {
        return repository.findByNombre(nombre).map(this::mapToDomain);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Departamento> findAll(String nombre) {
        return List.of();
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
        // ✨ ¡Mira qué hermoso y simple! Solo sacamos el ID si existe.
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
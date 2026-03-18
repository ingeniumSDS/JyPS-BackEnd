package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
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
        DepartamentoEntity entidadGuardada = repository.save(d); // ¡Aquí Spring ya le puso el ID!
        return mapToDomain(entidadGuardada); // Lo devolvemos al dominio con todo y ID
    }

    @Override
    public Optional<Departamento> findById(Long id) {
        // Busca en BD, y si lo encuentra, lo traduce al modelo de Dominio
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

        return new DepartamentoEntity(
                departamento.getId(),
                departamento.getNombre(),
                departamento.getDescripcion(),
                departamento.isActivo()
        );
    }

    private Departamento mapToDomain(DepartamentoEntity departamentoEntity) {
        return new Departamento(
                departamentoEntity.getId(),
                departamentoEntity.getNombre(),
                departamentoEntity.getDescripcion(),
                departamentoEntity.isActivo()
        );
    }
}

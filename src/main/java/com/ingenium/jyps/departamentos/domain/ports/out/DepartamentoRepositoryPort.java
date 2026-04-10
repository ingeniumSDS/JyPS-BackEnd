package com.ingenium.jyps.departamentos.domain.ports.out;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepositoryPort {

    long count();

    List<Departamento> buscarTodos();

    Departamento guardar(Departamento departamento);

    Optional<Departamento> buscarPorId(Long id);

    Optional<Departamento> buscarPorNombre(String name);

    Optional<Departamento> buscarPorJefeId(Long jefeId);


}

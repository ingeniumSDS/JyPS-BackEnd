package com.ingenium.jyps.departamentos.domain.ports.out;

import com.ingenium.jyps.departamentos.domain.model.Departamento;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepositoryPort {

    List<Departamento> findAll();

    Departamento save(Departamento departamento);

    Optional<Departamento> findById(Long id);

    Optional<Departamento> findByNombre(String name);



}

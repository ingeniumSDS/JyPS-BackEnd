package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Long> {

    Optional<JustificanteEntity> findById(long id);

    List<JustificanteEntity> findByEmpleado_Id(Long empleadoId);

    List<JustificanteEntity> findByJefe_Id(Long jefeId);
}

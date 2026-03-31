package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Integer> {

    List<Justificante> findByEmpleadoId(Long id);


}

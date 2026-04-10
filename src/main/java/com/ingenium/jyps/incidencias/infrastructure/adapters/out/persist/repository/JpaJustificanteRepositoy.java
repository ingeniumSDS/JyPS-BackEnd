package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Long> {

    Optional<JustificanteEntity> findById(long id);

}

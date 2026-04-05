package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Integer> {



}

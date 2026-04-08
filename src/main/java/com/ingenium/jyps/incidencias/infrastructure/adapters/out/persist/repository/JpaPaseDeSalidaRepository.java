package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaseDeSalidaRepository extends JpaRepository<PaseDeSalidaEntity, Long> {


}

package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaJustificanteRepository extends JpaRepository<JustificanteEntity, Long> {

    Optional<JustificanteEntity> findByFechaSolicitadaAndEmpleado_Id(LocalDate fechaSolicitada, Long empleadoId);

}

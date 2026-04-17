package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Long> {

    Optional<JustificanteEntity> findById(long id);


    @Query("SELECT J FROM JustificanteEntity J WHERE (J.fechaSolicitud >= :fechaSolicitudAfter AND J.fechaSolicitud <= :fechaSolicitudBefore) AND J.estado = 'APROBADO' ORDER BY J.fechaSolicitud ASC")
    List<JustificanteEntity> findByFechaSolicitudBetween(LocalDate fechaSolicitudAfter, LocalDate fechaSolicitudBefore);

    List<JustificanteEntity> findByEmpleado_IdOrderByFechaSolicitudDesc(Long empleadoId);


    List<JustificanteEntity> findByJefe_IdOrderByFechaSolicitudAsc(Long jefeId);
}

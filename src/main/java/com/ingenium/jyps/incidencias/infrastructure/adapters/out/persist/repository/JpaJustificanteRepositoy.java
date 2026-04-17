package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface JpaJustificanteRepositoy extends JpaRepository<JustificanteEntity, Long> {

    Optional<JustificanteEntity> findById(long id);

    @OrderBy("fechaSolicitud DESC")
    List<JustificanteEntity> findByEmpleado_Id(Long empleadoId);

    @OrderBy("fechaSolicitud ASC")
    List<JustificanteEntity> findByJefe_Id(Long jefeId);

    @Query("SELECT J FROM JustificanteEntity J WHERE (J.fechaSolicitud >= :fechaSolicitudAfter AND J.fechaSolicitud <= :fechaSolicitudBefore) AND J.estado = 'APROBADO' ORDER BY J.fechaSolicitud ASC")
    List<JustificanteEntity> findByFechaSolicitudBetween(LocalDate fechaSolicitudAfter, LocalDate fechaSolicitudBefore);
}

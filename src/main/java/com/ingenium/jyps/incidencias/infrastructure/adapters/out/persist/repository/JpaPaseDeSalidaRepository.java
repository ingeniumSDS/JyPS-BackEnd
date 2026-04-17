package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaPaseDeSalidaRepository extends JpaRepository<PaseDeSalidaEntity, Long> {

    List<PaseDeSalidaEntity> findByEmpleado_IdOrderByFechaSolicitudDesc(Long empleadoId);

    Optional<PaseDeSalidaEntity> findByQR(String qr);

    @Query("SELECT P FROM PaseDeSalidaEntity P WHERE (P.fechaSolicitud >= :fechaInicio " +
            "AND P.fechaSolicitud <= :fechaFin) AND P.estado IN ('A_TIEMPO', 'RETARDO') ORDER BY P.fechaSolicitud DESC")
    List<PaseDeSalidaEntity> findByFechaSolicitudBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT P FROM PaseDeSalidaEntity P WHERE P.empleado.id = :id AND P.estado IN ('PENDIENTE', 'APROBADO') AND P.fechaSolicitud = :fechaActual")
    Optional<PaseDeSalidaEntity> encontrarPendiente(Long id, LocalDate fechaActual);

    List<PaseDeSalidaEntity> findByJefe_IdOrderByFechaSolicitudAsc(Long jefeId);

}

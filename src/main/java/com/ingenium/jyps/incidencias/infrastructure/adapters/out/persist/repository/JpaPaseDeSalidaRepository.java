package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import jakarta.persistence.OrderBy;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaPaseDeSalidaRepository extends JpaRepository<PaseDeSalidaEntity, Long> {

    @OrderBy("fechaSolicitud DESC")
    List<PaseDeSalidaEntity> findByEmpleado_Id(Long empleadoId);

    @OrderBy("fechaSolicitud ASC")
    List<PaseDeSalidaEntity> findByJefe_Id(Long jefeId);

    Optional<PaseDeSalidaEntity> findByQR(String qr);

    @OrderBy("fechaSolicitud DESC")
    @Query("SELECT P FROM PaseDeSalidaEntity P WHERE (P.fechaSolicitud >= :fechaInicio " +
            "AND P.fechaSolicitud <= :fechaFin) AND P.estado IN ('A_TIEMPO', 'RETARDO')")
    List<PaseDeSalidaEntity> findByFechaSolicitudBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT P FROM PaseDeSalidaEntity P WHERE P.empleado.id = :id AND P.estado IN ('PENDIENTE', 'APROBADO') AND P.fechaSolicitud = :fechaActual")
    Optional<PaseDeSalidaEntity> encontrarPendiente(Long id, LocalDate fechaActual);
}

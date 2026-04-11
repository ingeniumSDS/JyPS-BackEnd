package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaPaseDeSalidaRepository extends JpaRepository<PaseDeSalidaEntity, Long> {


    List<PaseDeSalidaEntity> findByEmpleado_Id(Long empleadoId);

    List<PaseDeSalidaEntity> findByJefe_Id(Long jefeId);

    Optional<PaseDeSalidaEntity> findByQR(String qr);

    @Query("SELECT P FROM PaseDeSalidaEntity P WHERE (P.fechaSolicitud >= :fechaInicio " +
            "AND P.fechaSolicitud <= :fechaFin) AND P.estado IN ('A_TIEMPO', 'RETARDO')")
    List<PaseDeSalidaEntity> findByFechaSolicitudBetween(LocalDate fechaInicio, LocalDate fechaFin);
}

package com.ingenium.jyps.incidencias.domain.model;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Getter
public class Justificante {

    private Long id;
    private Long empleadoId;
    private Long jefeId;
    private LocalDate fechaSolicitada;
    private LocalDate fechaSolicitud;
    private String descripcion;
    private List<String> archivos;
    private EstadosIncidencia estado;

    // Constructor para mandar a crear un nuevo justificante, sin el ID que se genera automáticamente.
    public Justificante(
            Long empleadoId,
            Long jefeId,
            LocalDate fechaSolicitada,
            String descripcion,
            List<String> archivos
    ) {

        // Validación de los campos (El Modelo se defiende solo)

        validarDescripcion(descripcion);
        tieneEmpleado(empleadoId);
        tieneJefe(jefeId);
        validarFechaSolicitada(fechaSolicitada);

        this.empleadoId = empleadoId;
        this.jefeId = jefeId;
        this.fechaSolicitada = fechaSolicitada;
        this.fechaSolicitud = LocalDate.now();
        this.descripcion = descripcion;
        this.archivos = archivos;
        this.estado = EstadosIncidencia.PENDIENTE;
    }

    private void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        } else if (descripcion.trim().length() < 25) {
            throw new IllegalArgumentException("La descripción debe tener al menos 25 caracteres");
        } else if (descripcion.trim().length() > 255) {
            throw new IllegalArgumentException("La descripción no puede exceder los 255 caracteres");
        }
    }

    private void validarFechaSolicitada(LocalDate fechaSolicitada) {

        int diasAtras = 3;

        if (fechaSolicitada.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                fechaSolicitada.getDayOfWeek() == DayOfWeek.THURSDAY ||
                fechaSolicitada.getDayOfWeek() == DayOfWeek.FRIDAY) {
            diasAtras = 5;
        }

        if (fechaSolicitada.isBefore(LocalDate.now().minusDays(diasAtras))) {
            throw new IllegalArgumentException("El justificante no puede ser anterior a 3 días hábiles.");
        }
    }

    private void tieneJefe(Long jefeId) {
        if (jefeId == null) {
            throw new IllegalArgumentException("El ID del jefe no puede ser nulo");
        }
    }

    private void tieneEmpleado(Long empleadoId) {
        if (empleadoId == null) {
            throw new IllegalArgumentException("El ID del empleado no puede ser nulo");
        }
    }

    public void rechazar() {
        this.estado = EstadosIncidencia.RECHAZADO;
    }

    public void aprobar() {
        this.estado = EstadosIncidencia.APROBADO;
    }

    public void caducar() {
        this.estado = EstadosIncidencia.CADUCADO;
    }


}
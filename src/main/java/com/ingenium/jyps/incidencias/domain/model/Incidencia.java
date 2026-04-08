package com.ingenium.jyps.incidencias.domain.model;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Incidencia {

    protected Long id;
    protected Long empleadoId;
    protected Long jefeId;
    protected LocalDate fechaSolicitud;
    protected String descripcion;
    protected List<String> archivos;
    protected EstadosIncidencia estado;
    protected String motivoRechazo;


    //==============//
    // VALIDACIONES //
    //==============//

    public void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        } else if (descripcion.trim().length() < 25) {
            throw new IllegalArgumentException("La descripción debe tener al menos 25 caracteres");
        } else if (descripcion.trim().length() > 255) {
            throw new IllegalArgumentException("La descripción no puede exceder los 255 caracteres");
        }
        this.descripcion = descripcion.trim();
    }

    public void tieneJefe(Long jefeId) {
        if (jefeId == null) {
            throw new IllegalArgumentException("El ID del jefe no puede ser nulo");
        }
        this.jefeId = jefeId;

    }

    public void tieneEmpleado(Long empleadoId) {
        if (empleadoId == null) {
            throw new IllegalArgumentException("El ID del empleado no puede ser nulo");
        }

        this.empleadoId = empleadoId;

    }

    public void validarMotivoRechazo(String motivoRechazo) {

        if (motivoRechazo.isEmpty()) {
            throw new IllegalArgumentException("Debe incluír los motivos del rechazo.");
        }

        if (motivoRechazo.length() < 25) {
            throw new IllegalArgumentException("El motivo de rechazo debe tener al menos 25 caracteres.");
        }

        if (motivoRechazo.length() > 255) {
            throw new IllegalArgumentException("El motivo de rechazo no puede exceder los 255 caracteres.");
        }

        this.motivoRechazo = motivoRechazo;

    }

    //=========//
    // ESTADOS //
    //=========//

    public void rechazar() {
        validarMotivoRechazo(motivoRechazo);
        this.estado = EstadosIncidencia.RECHAZADO;
    }

    public void aprobar() {
        this.estado = EstadosIncidencia.APROBADO;
    }

    public void caducar() {
        if (this.estado == EstadosIncidencia.PENDIENTE || this.estado == EstadosIncidencia.APROBADO) {
            this.estado = EstadosIncidencia.CADUCADO;
        }
    }

}

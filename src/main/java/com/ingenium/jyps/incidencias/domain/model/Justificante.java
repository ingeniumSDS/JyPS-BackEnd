package com.ingenium.jyps.incidencias.domain.model;

import com.ingenium.jyps.config.mappstruct.Default;
import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
public class Justificante extends Incidencia {


    private LocalDate fechaSolicitada;


    // Constructor para mandar a guardar un nuevo justificante, sin el ID que se genera automáticamente.
    public Justificante(
            Long empleadoId,
            Long jefeId,
            LocalDate fechaSolicitada,
            String descripcion,
            List<String> archivos
    ) {

        // Validamos la información esencial y asignamos los valores si estos son válidos.
        validarDescripcion(descripcion);
        tieneEmpleado(empleadoId);
        tieneJefe(jefeId);
        validarFechaSolicitada(fechaSolicitada);

        this.fechaSolicitud = LocalDate.now();
        this.archivos = archivos;
        this.estado = EstadosIncidencia.PENDIENTE;
    }

    // Constructor para rehidratar el justificante desde la base de datos, con el ID incluido.
    @Default
    public Justificante(

            Long id,
            Long empleadoId,
            Long jefeId,
            LocalDate fechaSolicitada,
            LocalDate fechaSolicitud,
            String descripcion,
            List<String> archivos,
            String comentario,
            EstadosIncidencia estado,
            String nombreCompleto,
            String correoEmpleado

    ) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.jefeId = jefeId;
        this.fechaSolicitada = fechaSolicitada;
        this.fechaSolicitud = fechaSolicitud;
        this.descripcion = descripcion;
        this.archivos = archivos;
        this.estado = estado;
        this.comentario = comentario;
        this.nombreCompleto = nombreCompleto;
        this.correoEmpleado = correoEmpleado;
    }

    // Valida que no se hayan excedido los 3 días HÁBILES permitidos para
    // guardar un justificante Y NO permite fechas futuras.

    private void validarFechaSolicitada(LocalDate fechaSolicitada) {

        int diasAtras = 3;

        if (fechaSolicitada.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                fechaSolicitada.getDayOfWeek() == DayOfWeek.THURSDAY ||
                fechaSolicitada.getDayOfWeek() == DayOfWeek.FRIDAY) {
            diasAtras = 5;
        }

        if (fechaSolicitada.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("El justificante no puede ser solicitado para un día no laborable.");
        }

        if (fechaSolicitada.isBefore(LocalDate.now().minusDays(diasAtras))) {
            throw new IllegalArgumentException("El justificante no puede ser anterior a 3 días hábiles.");
        }

        if (fechaSolicitada.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("El justificante no puede ser posterior a la fecha actual.");
        }

        this.fechaSolicitada = fechaSolicitada;

    }

}
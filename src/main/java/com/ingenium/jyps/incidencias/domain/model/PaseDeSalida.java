package com.ingenium.jyps.incidencias.domain.model;

import com.ingenium.jyps.config.mappstruct.Default;
import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.users.domain.model.Usuario;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PaseDeSalida extends Incidencia {


    // Extras para el dominio del pase de salida
    private Usuario empleado;
    private LocalDateTime horaSolicitada;
    private LocalDateTime horaEsperada;
    private LocalDateTime horaSalidaReal;


    // Constructor para mandar a crear un nuevo pasa de salida, sin el ID que se genera automáticamente.
    public PaseDeSalida(Long empleadoId,
                        Long jefeId,
                        LocalDate fechaSolicitud,
                        String descripcion,
                        List<String> archivos,
                        EstadosIncidencia estado,
                        Usuario empleado,
                        LocalDateTime horaSolicitada) {

        this.empleadoId = empleadoId;
        this.jefeId = jefeId;
        this.fechaSolicitud = fechaSolicitud;
        this.descripcion = descripcion;
        this.archivos = archivos;
        this.estado = estado;
        this.empleado = empleado;
        this.horaSolicitada = horaSolicitada;
    }


    @Default
    public PaseDeSalida(
            Long id,
            Long empleadoId,
            Long jefeId,
            LocalDateTime horaSolicitada,
            LocalDate fechaSolicitud,
            String descripcion,
            List<String> archivos,
            EstadosIncidencia estado
    ) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.jefeId = jefeId;
        this.horaSolicitada = horaSolicitada;
        this.fechaSolicitud = fechaSolicitud;
        this.descripcion = descripcion;
        this.archivos = archivos;
        this.estado = estado;
    }

    // Validaciones y métodos para la creación del PASE DE SALIDA
    private void validarHoraSolicitada(LocalDateTime horaSolicitada) {

        // Impedir que pida un horario anterior al actual.
        if (horaSolicitada.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La hora solicitada no puede ser anterior a la hora actual.");
        }

        // Validar que se esté solicitando para el día en curso.
        if (horaSolicitada.toLocalDate().isAfter(LocalDate.now())
                || horaSolicitada.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La hora solicitada no puede ser para un día distinto al actual.");
        }
    }


    // FLUJO DE ESTADOS PARA EL PASE DE SALIDA
    // Verifica si el empleado debe volver o si el periodo de gracia coincide con el fin de su jornada.
    public void debeVolver() {
        if (empleado.getHoraSalida().equals(horaEsperada.toLocalTime())
                || empleado.getHoraSalida().isBefore(horaEsperada.toLocalTime())) {
            this.estado = EstadosIncidencia.A_TIEMPO;
        }
    }

    // Registra la hora de salida REAL del empleado al pasar por caseta de vigilancia.
    public void checkOut() {
        this.horaSalidaReal = LocalDateTime.now();
        this.horaEsperada = horaSalidaReal.plusHours(3);
    }

    public void checkIn() {
        this.estado = EstadosIncidencia.RETARDO;
    }

}


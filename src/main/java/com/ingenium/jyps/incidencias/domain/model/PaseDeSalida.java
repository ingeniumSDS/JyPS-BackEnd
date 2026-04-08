package com.ingenium.jyps.incidencias.domain.model;

import com.ingenium.jyps.config.mappstruct.Default;
import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.users.domain.model.Usuario;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class PaseDeSalida extends Incidencia {



    // Extras para el dominio del pase de salida
    private Usuario empleado;
    private LocalDateTime horaSolicitada;
    private LocalDateTime horaEsperada;
    private LocalDateTime horaSalidaReal;
    private String QR;


    // Constructor para mandar a crear un nuevo pasa de salida, sin el ID que se genera automáticamente.
    public PaseDeSalida(
            Long empleadoId,
            Long jefeId,
            String descripcion,
            List<String> archivos,
            Usuario empleado,
            LocalDateTime horaSolicitada) {

        validarHoraSolicitada(horaSolicitada);
        tieneEmpleado(empleadoId);
        tieneJefe(jefeId);
        validarDescripcion(descripcion);

        // Se toma la fecha del día en curso por defecto.
        this.fechaSolicitud = LocalDate.now();

        this.archivos = archivos;
        // Por defecto se crea como pendiente
        this.estado = EstadosIncidencia.PENDIENTE;
        this.empleado = empleado;

    }

    // Constuctor para rehidratar desde la base de datos.
    @Default
    public PaseDeSalida(
            // Datos de identificación/
            Long id,
            Long empleadoId,
            Long jefeId,
            LocalDate fechaSolicitud,

            // Datos inherentes al pase de salida.
            LocalDateTime horaSolicitada,
            String descripcion,
            List<String> archivos,

            // Momento en que el empleado parsa por la caseta de vigilancia.
            LocalDateTime horaSalidaReal,

            // Se calcula en funición de la hora de salida real
            LocalDateTime horaEsperada,
            EstadosIncidencia estado
    ) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.jefeId = jefeId;
        this.horaSolicitada = horaSolicitada;
        this.fechaSolicitud = fechaSolicitud;
        this.horaEsperada = horaEsperada;
        this.horaSalidaReal = horaSalidaReal;
        this.descripcion = descripcion;
        this.archivos = archivos;
        this.estado = estado;
    }


    //==============//
    // VALIDACIONES //
    //==============//

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

        this.horaSolicitada = horaSolicitada;

    }

    //========================================//
    // FLUJO DE ESTADOS PARA EL PASE DE SALIDA//
    //========================================//

    // Verifica si el empleado debe volver o si el periodo de gracia coincide con el fin de su jornada.
    // Se ejecuta al momento que el empleado hace checkout.
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
        debeVolver();
    }

    public void checkIn() {
        this.horaSalidaReal = LocalDateTime.now();
        if (horaEsperada.toLocalDate().isAfter(LocalDate.now())) {
            this.estado = EstadosIncidencia.RETARDO;
        } else {
            this.estado = EstadosIncidencia.A_TIEMPO;
        }
    }

    public void generarQR(){
        this.QR = UUID.randomUUID().toString();
    }

}


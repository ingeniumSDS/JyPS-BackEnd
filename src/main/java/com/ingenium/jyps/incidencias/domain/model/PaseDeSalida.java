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
    private LocalDateTime horaSolicitada;
    private LocalDateTime horaEsperada;
    private LocalDateTime horaSalidaReal;
    private LocalDateTime horaRetornoReal;
    private String QR;




    // Constructor para mandar a guardar un nuevo pasa de salida, sin el ID que se genera automáticamente.
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
        this.horaEsperada = horaSolicitada.plusHours(3);

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
            LocalDateTime horaRetornoReal,
            EstadosIncidencia estado,
            String QR,
            String comentario,
            String nombreCompletoEmpleado,
            String correoEmpleado
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
        this.QR = QR;
        this.comentario = comentario;
        this.nombreCompleto = nombreCompletoEmpleado;
        this.horaRetornoReal = horaRetornoReal;
        this.correoEmpleado = correoEmpleado;
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
        } else {
            this.estado = EstadosIncidencia.FUERA;
        }
    }

    public void check() {
        if (this.estado == EstadosIncidencia.CADUCADO || this.estado == EstadosIncidencia.A_TIEMPO || this.estado == EstadosIncidencia.RETARDO) {
            throw new IllegalStateException("El pase de salida ya ha sido usado o ha caducado, no se puede usar nuevamente.");
        }

        if (this.estado == EstadosIncidencia.APROBADO) {
            checkOut();
        } else if (this.estado == EstadosIncidencia.FUERA) {
            checkIn();
        } else {
            throw new IllegalStateException("El pase aún está PENDIENTE o en un estado inválido para check.");
        }


    }

    // Registra la hora de salida REAL del empleado al pasar por caseta de vigilancia.
    public void checkOut() {
        if (LocalDateTime.now().isBefore(horaSolicitada)) {
            throw new IllegalArgumentException("El empleado no puede hacer check-out antes de la hora esperada.");
        }
        this.horaSalidaReal = LocalDateTime.now();
        this.horaEsperada = horaSalidaReal.plusHours(3);
        debeVolver();
    }


    public void checkIn() {
        this.horaRetornoReal = LocalDateTime.now();
        if (horaRetornoReal.isAfter(horaEsperada.plusMinutes(5))) {
            this.estado = EstadosIncidencia.RETARDO;
        } else {
            this.estado = EstadosIncidencia.A_TIEMPO;
        }
    }

    public void generarQR() {
        this.QR = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }


    public void revisar(EstadosIncidencia estado, String comentario) {
        if (estado.equals(EstadosIncidencia.APROBADO)) {
            aprobar();
            generarQR();
            this.comentario = comentario;
        } else if (estado.equals(EstadosIncidencia.RECHAZADO)) {
            rechazar(comentario);
        } else {
            throw new IllegalArgumentException("El estado debe ser APROBADO o RECHAZADO.");
        }
    }
}


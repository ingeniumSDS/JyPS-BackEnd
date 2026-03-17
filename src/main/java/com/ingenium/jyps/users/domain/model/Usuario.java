package com.ingenium.jyps.users.domain.model;

import com.ingenium.jyps.users.domain.model.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class Usuario {
    @Setter
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private List<Roles> roles;

    private Cuenta cuenta;

    public Usuario(String nombre,
                   String apellidoPaterno,
                   String apellidoMaterno,
                   String correo,
                   String telefono,
                   LocalTime horaEntrada,
                   LocalTime horaSalida,
                   List<Roles> roles) {

        validarCampoTexto(nombre, "nombre", 100);
        validarCampoTexto(apellidoPaterno, "apellido paterno", 50);
        validarCampoTexto(apellidoMaterno, "apellido materno", 50);
        validarCorreo(correo);
        validarTelefono(telefono);
        validarRoles(roles);

        if (roles.contains(Roles.EMPLEADO)) {
            validarJornada(horaEntrada, horaSalida);
            this.horaEntrada = horaEntrada;
            this.horaSalida = horaSalida;
        } else {
            this.horaEntrada = null;
            this.horaSalida = null;
        }

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.correo = correo.trim().toLowerCase();
        this.telefono = telefono.trim();
        this.roles = roles;
    }

    // Constructor para Rehidratar (recibe el ID y TODO el estado, incluyendo la Cuenta)
    public Usuario(Long id, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String correo, String telefono, LocalTime horaEntrada, LocalTime horaSalida,
                   List<Roles> roles, Cuenta cuenta) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.roles = roles;
        this.cuenta = cuenta;
    }

    private void validarCampoTexto(String campo, String nombreCampo, int longitudMaxima) {

        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El " + nombreCampo + " es obligatorio.");
        }

        if (campo.length() < 2) {
            throw new IllegalArgumentException("El " + nombreCampo + " es muy corto.");
        } else if (campo.length() > longitudMaxima) {
            throw new IllegalArgumentException("El " + nombreCampo + " es demasiado largo.");
        }

        if (nombreCampo.equals("nombre") || nombreCampo.equals("apellido paterno") || nombreCampo.equals("apellido materno")) {
            validarCaracteres(campo, nombreCampo);
        }
    }

    private void validarCaracteres(String campo, String nombreCampo) {
        if (!campo.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            throw new IllegalArgumentException("El campo no admite caracteres especiales.");
        }
    }

    private void validarCorreo(String correo) {

        validarCampoTexto(correo, "correo", 255);

        if (!correo.matches("^[a-zA-Z0-9.]+@utez\\.edu\\.mx$")) {
            throw new IllegalArgumentException("El correo debe pertenecer al dominio @utez.edu.mx");

        }

    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        } else if (!telefono.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("El teléfono debe tener un formato válido a 10 dígitos.");
        }
    }

    private void validarJornada(LocalTime horaEntrada, LocalTime horaSalida) {
        if (horaEntrada == null || horaSalida == null) {
            throw new IllegalArgumentException("Debe establecer una jornada para el usuario.");
        }

        if (horaEntrada.isAfter(horaSalida) || horaEntrada.equals(horaSalida)) {
            throw new IllegalArgumentException("La hora de salida debe ser posterior a la hora de entrada.");
        }
    }


    private void validarRoles(List<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }
    }

    public void asignarCuenta(Cuenta nuevaCuenta) {
        this.cuenta = nuevaCuenta;
    }

    public String nombreCompleto() {
        return this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
    }


}
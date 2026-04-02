package com.ingenium.jyps.users.domain.model;

import com.ingenium.jyps.users.domain.model.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.regex.Pattern;

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
    @Setter
    private String nombreDepartamento;
    private Long departamentoId;

    private Cuenta cuenta;

    private static final Pattern PATTERN = Pattern.compile("^[\\p{L}+$]", Pattern.UNICODE_CHARACTER_CLASS);

    // Constructor para crear un nuevo usuario, no se incluye ID ya que es generado automáticamente por la base de datos, y no se incluye la Cuenta ya que se asigna posteriormente.
    public Usuario(String nombre,
                   String apellidoPaterno,
                   String apellidoMaterno,
                   String correo,
                   String telefono,
                   LocalTime horaEntrada,
                   LocalTime horaSalida,
                   List<Roles> roles,
                   Long departamentoId
    ) {

        validarCampoTexto(nombre, "nombre", 50);
        validarCampoTexto(apellidoPaterno, "apellido paterno", 50);
        validarCampoTexto(apellidoMaterno, "apellido materno", 50);
        validarCorreo(correo);
        validarTelefono(telefono);
        validarDepartamento(departamentoId);
        validarRoles(roles);

        if (!roles.contains(Roles.GUARDIA)) {
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
        this.departamentoId = departamentoId;
    }

    // Constructor para Rehidratar (recibe el ID y TODO el estado, incluyendo la Cuenta)
    public Usuario(Long id, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String correo, String telefono, LocalTime horaEntrada, LocalTime horaSalida,
                   List<Roles> roles, Long departamentoId, Cuenta cuenta) {

        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.roles = roles;
        this.departamentoId = departamentoId;
        this.cuenta = cuenta;

    }


    // Validador genérico para campos de texto.
    private void validarCampoTexto(String campo, String nombreCampo, int longitudMaxima) {

        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El " + nombreCampo + " es obligatorio.");
        }

        if (campo.trim().length() < 2) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe contener al menos 2 caracteres");
        } else if (campo.trim().length() > longitudMaxima) {
            throw new IllegalArgumentException("El " + nombreCampo + " es debe contener como máximo " + longitudMaxima + " caracteres.");
        }

        if (nombreCampo.equals("nombre") || nombreCampo.equals("apellido paterno") || nombreCampo.equals("apellido materno")) {
            validarCaracteres(campo, nombreCampo);
        }
    }


    // Impide que el usuario sea registrado sin ser asignado a un departamento.
    private void validarDepartamento(Long departamentoId) {
        if (departamentoId == null) {
            throw new IllegalArgumentException("Debe seleccionar un departamento.");
        }
    }

    // Impide ingresar caracteres especiales al campo validado.
    private void validarCaracteres(String campo, String nombreCampo) {

        if (!PATTERN.matcher(campo).matches()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + "  admite caracteres especiales.");
        }

    }


    // Valida que el correo tenga un formato válido, no sea nulo y pertenezca al dominio institucional
    private void validarCorreo(String correo) {

        validarCampoTexto(correo, "correo", 255);

        if (!correo.matches("^[a-zA-Z0-9.]+@utez\\.edu\\.mx$")) {
            throw new IllegalArgumentException("El correo debe pertenecer al dominio @utez.edu.mx");
        } else if (correo.trim().length() < 20) {
            throw new IllegalArgumentException("El correo es demasiado corto.");
        }

    }

    // Valida que el teléfono tenga un formato válido a 10 dígitos y no sea nulo.
    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El telefono es obligatorio.");
        } else if (!telefono.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("El teléfono debe tener un formato válido a 10 dígitos.");
        }
    }

    // Valida que la hora de entrada sea después de la hora de salida.
    private void validarJornada(LocalTime horaEntrada, LocalTime horaSalida) {
        if (horaEntrada == null || horaSalida == null) {
            throw new IllegalArgumentException("Debe establecer una jornada para el usuario.");
        }

        if (horaEntrada.isAfter(horaSalida) || horaEntrada.equals(horaSalida)) {
            throw new IllegalArgumentException("La hora de salida debe ser posterior a la hora de entrada.");
        }
    }


    // Valida que el usuario tenga al menos un rol asignado, y que no sea nulo o vacío.
    private void validarRoles(List<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }
    }

    // Asignamos al usuario una nueva cuenta.
    public void asignarCuenta(Cuenta nuevaCuenta) {
        this.cuenta = nuevaCuenta;
    }

    // Recuperamos el nombre completo del usuario.
    public String nombreCompleto() {
        return this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
    }


    // Método para actualizar los datos personales del usuario, con validaciones similares a las del constructor
    public void actualizarDatosPersonales(String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String telefono, LocalTime horaEntrada, LocalTime horaSalida, List<Roles> roles, Long departamentoId) {

        validarCampoTexto(nombre, "nombre", 50);
        validarCampoTexto(apellidoPaterno, "apellido paterno", 50);
        validarCampoTexto(apellidoMaterno, "apellido materno", 50);
        validarCorreo(correo);
        validarTelefono(telefono);
        validarJornada(horaEntrada, horaSalida);
        validarRoles(roles);
        validarDepartamento(departamentoId);

        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.roles = roles;
        this.departamentoId = departamentoId;
    }
}
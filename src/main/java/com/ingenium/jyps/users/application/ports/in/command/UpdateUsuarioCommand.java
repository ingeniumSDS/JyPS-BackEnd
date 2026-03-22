package com.ingenium.jyps.users.application.ports.in.command;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.users.domain.model.enums.Roles;

import java.time.LocalTime;
import java.util.List;

public record UpdateUsuarioCommand(
    Long id,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    String correo,
    String telefono,
    LocalTime horaEntrada,
    LocalTime horaSalida,
    List<Roles> roles,
    Departamento departamentoId

) {}

package com.ingenium.jyps.incidencias.application.ports.out;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.ArchivoAdjunto;

import java.util.List;

public interface StoragePort {

    List<String> guardarArchivos(Long idEmpleado, List<ArchivoAdjunto> archivos);

}

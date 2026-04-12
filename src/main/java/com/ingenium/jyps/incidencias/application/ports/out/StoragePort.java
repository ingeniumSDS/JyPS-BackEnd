package com.ingenium.jyps.incidencias.application.ports.out;

import com.ingenium.jyps.incidencias.domain.model.ArchivoAdjunto;

import java.util.List;

public interface StoragePort {

    List<String> guardarArchivos(Long idEmpleado, List<ArchivoAdjunto> archivos);

    byte[] leerArchivo(Long idEmpleado, String nombreArchivo);
}

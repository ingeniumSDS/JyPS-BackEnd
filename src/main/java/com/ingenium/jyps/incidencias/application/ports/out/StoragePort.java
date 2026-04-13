package com.ingenium.jyps.incidencias.application.ports.out;

import com.ingenium.jyps.incidencias.domain.model.ArchivoAdjunto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface StoragePort {

    List<String> guardarArchivos(Long idEmpleado, List<ArchivoAdjunto> archivos);

    Resource leerArchivo(Long idEmpleado, String nombreArchivo);
}

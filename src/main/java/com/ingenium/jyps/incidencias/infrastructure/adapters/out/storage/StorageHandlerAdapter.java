package com.ingenium.jyps.incidencias.infrastructure.adapters.out.storage;

import com.ingenium.jyps.incidencias.domain.model.ArchivoAdjunto;
import com.ingenium.jyps.incidencias.application.ports.out.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class StorageHandlerAdapter implements StoragePort {

    @Value("${app.storage.location}")
    private String rootPath;

    @Override
    public List<String> guardarArchivos(Long idEmpleado, List<ArchivoAdjunto> archivos) {
        // 1. Definimos la ruta de la carpeta del empleado
        List<String> nombresFinales = new ArrayList<>();
        Path directorioEmpleado = Path.of(rootPath, idEmpleado.toString()).normalize();
        try {
            // 2. Creamos el directorio (y sus padres si no existen)
            Files.createDirectories(directorioEmpleado);

            // 3. Iteramos y guardamos cada archivo
            for (ArchivoAdjunto archivo : archivos) {
                // Resolvemos la ruta final: storage/empleados/1/mi_archivo.pdf
                // Resultado: 550e8400-e29b_receta.pdf
                String nombreUnico = UUID.randomUUID() + "_" + archivo.nombreOriginal();
                Path rutaArchivoFinal = directorioEmpleado.resolve(nombreUnico);
                // Escribimos los bytes en el disco
                Files.write(rutaArchivoFinal, archivo.contenido());
                nombresFinales.add(nombreUnico);


            }
            return nombresFinales;

        } catch (IOException e) {
            // Aquí podrías lanzar una excepción personalizada de tu dominio
            throw new RuntimeException("Error al persistir archivos en disco", e);
        }
    }

    // En StorageHandlerAdapter
    @Override
    public byte[] leerArchivo(Long empleadoId, String nombreArchivo) {
        Path ruta = Path.of(rootPath, empleadoId.toString()).resolve(nombreArchivo).normalize();
        try {
            return Files.readAllBytes(ruta);
        } catch (IOException e) {
            throw new RuntimeException("Archivo no encontrado", e);
        }
    }

}

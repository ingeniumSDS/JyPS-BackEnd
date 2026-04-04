package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.application.ports.out.StoragePort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileSystemStorageAdapter implements StoragePort {

    // Define una raíz base (podrías sacarla de application.properties)
    private static final String ROOT_DIR = "uploads";
    private static final String SUB_DIR = "justificantes";

    @Override
    public String guardar(byte[] contenido, String nombreOriginal, Long idEmpleado) {
        try {
            Path folder = Paths.get(ROOT_DIR, "emp" + idEmpleado);
            Files.createDirectories(folder);

            String nombreUnico = UUID.randomUUID() + "_" + nombreOriginal;
            Files.write(folder.resolve(nombreUnico), contenido);

            return nombreUnico; // Devolvemos solo el nombre para la DB
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir archivo", e);
        }
    }
}

package com.ingenium.jyps.incidencias.application.ports.out;


public interface StoragePort {
    String guardar(byte[] contenido, String nombreOriginal, Long idUsuario);
}

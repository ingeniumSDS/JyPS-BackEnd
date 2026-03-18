package com.ingenium.jyps.departamentos.domain.model;

import lombok.Getter;

@Getter
public class Departamento {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;

    // CONSTRUCTOR 1: Para crear un nuevo departamento (Nacimiento)
    public Departamento(String nombre, String descripcion) {
        validarCampoObligatorio(nombre, "nombre");
        this.nombre = nombre.trim().toUpperCase(); // Un buen toque es normalizar el nombre

        // Manejamos la descripción opcional
        if (descripcion == null || descripcion.trim().isEmpty()) {
            this.descripcion = "Sin descripción";
        } else {
            this.descripcion = descripcion.trim();
        }

        this.activo = true; // Por defecto nace activo
    }

    // CONSTRUCTOR 2: Para rehidratar desde la base de datos (Adaptador)
    public Departamento(Long id, String nombre, String descripcion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    private void validarCampoObligatorio(String campo, String nombreCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío.");
        }
    }

    // Métodos de negocio para cambiar el estado (sin usar setters)
    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }
}
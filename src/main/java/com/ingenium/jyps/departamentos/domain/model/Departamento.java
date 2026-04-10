package com.ingenium.jyps.departamentos.domain.model;

import lombok.Getter;

@Getter
public class Departamento {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private Long jefeId;

    // CONSTRUCTOR 1: Para crear un nuevo departamento (Nacimiento)
    public Departamento(String nombre, String descripcion, Long jefeId) {

        validarCampoObligatorio(nombre);
        this.nombre = nombre.trim().toUpperCase(); // Un buen toque es normalizar el nombre

        // Manejamos la descripción opcional
        if (descripcion == null || descripcion.trim().isEmpty()) {
            this.descripcion = "Sin descripción";
        } else {
            this.descripcion = descripcion.trim();
        }

        if (jefeId == null || jefeId <= 0) {
            this.activo = false; // Por defecto nace inactivo
        } else {
            this.activo = true;
            this.jefeId = jefeId;
        }
    }

    // CONSTRUCTOR 2: Para rehidratar desde la base de datos (Adaptador)
    public Departamento(Long id, String nombre, String descripcion, boolean activo, Long jefeId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.jefeId = jefeId;
    }

    private void validarCampoObligatorio(String campo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public void asignarJefe(Long jefeId) {
        this.jefeId = jefeId;
        if (!this.activo) {
            this.activo = true; // Si se asigna un jefe, el departamento se activa
        }
    }


    public void cambiarEstado() {
        this.activo = !this.activo;
    }

    public void actualizarDatos(
            String nombre,
            String descripcion,
            boolean activo,
            long jefeId
    ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.jefeId = jefeId;
    }

}
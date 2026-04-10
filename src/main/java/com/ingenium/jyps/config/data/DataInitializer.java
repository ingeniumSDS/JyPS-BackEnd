package com.ingenium.jyps.config.data;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.users.application.ports.in.usecases.GuardarUsuarioUseCase;
import com.ingenium.jyps.users.application.ports.in.usecases.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("dev") // Solo se ejecutará en el perfil de desarrollo
public class DataInitializer implements CommandLineRunner {

    private final DepartamentoRepositoryPort departamentoRepositoryPort;
    private final GuardarUsuarioUseCase guardarUsuarioUseCase;

    @Override
    public void run(String @NonNull ... args) throws Exception {

        if (departamentoRepositoryPort.count() == 0) {
            // 1. Guardamos el departamento y recuperamos el objeto persistido
            Departamento deptoTemp = new Departamento(
                    "DEVELOPMENT",
                    "Departamento de pruebas para el sistema.",
                    null
            );

            // Importante: Recuperar el departamento guardado para obtener su ID real
            Departamento deptoGuardado = departamentoRepositoryPort.guardar(deptoTemp);

            String dominio = "@utez.edu.mx";
            // 2. Crear el comando usando el ID real generado por la BD
            List<String> correosAdministrador = List.of(
                    "20243ds108" + dominio,
                    "20243ds106" + dominio,
                    "20243ds097" + dominio
            );

            List<String> telefonosAdministrador = List.of(
                    "7771234567",
                    "7771234568",
                    "7771234569"
            );

            for ( int i = 0; i < correosAdministrador.size(); i++) {
                RegistrarUsuarioCommand admin = new RegistrarUsuarioCommand(
                        "ADMIN" + (i + 1), // Nombres únicos para evitar conflictos
                        "ADMIN",
                        "ADMIN",
                        correosAdministrador.get(i), // Correos únicos para cada administrador
                        telefonosAdministrador.get(i), // Teléfonos únicos para cada administrador
                        LocalDateTime.now().toLocalTime(),
                        LocalDateTime.now().toLocalTime().plusHours(1), // Un turno más realista
                        List.of(Roles.ADMINISTRADOR),
                        deptoGuardado.getId() // <-- Usar el ID dinámico
                );

                guardarUsuarioUseCase.ejecutar(admin);

            }

            System.out.println(">>> DataInitializer: Datos de desarrollo cargados con éxito.");

        } else {
            // Cambiamos el error por un mensaje de omitir
            System.out.println(">>> DataInitializer: La base de datos ya contiene datos, omitiendo inicialización.");
        }
    }
}
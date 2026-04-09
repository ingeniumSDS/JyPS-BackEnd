package com.ingenium.jyps.users.domain.repository;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

//Esta interfaz es el puerto de salida
// para el repositorio de usuarios,
// define las operaciones que se pueden realizar
// con los usuarios en la capa de dominio.
// La implementación de esta interfaz se encargará
// de interactuar con la base de datos u otro
// sistema de almacenamiento para persistir
// y recuperar los datos de los usuarios.

public interface UsuarioRepositoryPort {

    Usuario crear(Usuario usuario);
    List<Usuario> buscarTodos();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorCorreo(String email);
    Optional<Usuario> buscarPorTelefono(String telefono);
    Optional<Usuario> buscarPorToken(String token);

    boolean existsByCorreo(String correo);
    boolean existsByTelefono(String telefono);

    long contarPorDepartamento(Long id);
    List<Usuario> filtrarPorDepartamento(Long idDepartamento);
}

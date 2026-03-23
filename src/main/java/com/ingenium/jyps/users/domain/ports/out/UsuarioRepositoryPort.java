package com.ingenium.jyps.users.domain.ports.out;

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

    Usuario save(Usuario usuario);

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByCorreo(String email);

    Optional<Usuario> findByTelefono(String email);


    boolean estaActivo(String correo);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String correo);

}

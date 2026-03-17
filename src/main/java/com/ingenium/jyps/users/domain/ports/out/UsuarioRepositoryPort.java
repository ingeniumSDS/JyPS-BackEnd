package com.ingenium.jyps.users.domain.ports.out;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

   void save(Usuario usuario);

   Optional<Usuario> findByCorreo(String email);

   Optional<Usuario> findByTelefono(String email);

   Optional<Usuario> findById(Long id);

   boolean deleteById(String email);


}

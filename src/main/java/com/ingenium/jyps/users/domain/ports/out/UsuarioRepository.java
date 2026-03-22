package com.ingenium.jyps.users.domain.ports.out;

import com.ingenium.jyps.users.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

   Usuario save(Usuario usuario);

   Optional<Usuario> findByCorreo(String email);

   Optional<Usuario> findByTelefono(String email);

   Optional<Usuario> findById(Long id);

   List<Usuario> findAll();

   boolean deleteById(String email);


}

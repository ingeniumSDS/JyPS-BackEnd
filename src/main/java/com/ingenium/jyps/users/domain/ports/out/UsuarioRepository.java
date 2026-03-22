package com.ingenium.jyps.users.domain.ports.out;

import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.response.UsuarioResponse;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByCorreo(String email);

    Optional<Usuario> findByTelefono(String email);


    boolean deleteById(String email);


}

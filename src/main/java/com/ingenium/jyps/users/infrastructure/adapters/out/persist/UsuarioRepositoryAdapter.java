package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.repository.JpaUsuarioRepository;
import com.ingenium.jyps.utils.mappers.UsuarioMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpaUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository, UsuarioMapper usuarioMapper) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity uE = usuarioMapper.toEntity(usuario);
        UsuarioEntity entidadGuardada = jpaUsuarioRepository.save(uE);
        return usuarioMapper.toDomain(entidadGuardada);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return jpaUsuarioRepository.findByCorreo(correo).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByTelefono(String telefono) {
        return jpaUsuarioRepository.findByTelefono(telefono).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByToken(String token) {
        return jpaUsuarioRepository.findByCuenta_TokenRecuperacion(token).map(usuarioMapper::toDomain);
    }

    @Override
    public boolean estaActivo(String correo) {
        Usuario usuario = jpaUsuarioRepository.findByCorreo(correo).map(usuarioMapper::toDomain).orElseThrow( () ->
                new  RuntimeException("Usuario no encontrado"));
        if (usuario.getCuenta() != null) {
            Cuenta cuenta = usuario.getCuenta();
            return cuenta.isActiva();
        }
        return false;
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return jpaUsuarioRepository.findByCorreo(correo).isPresent();
    }

    @Override
    public boolean existsByTelefono(String telefono) {
        return jpaUsuarioRepository.findByTelefono(telefono).isPresent();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaUsuarioRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaUsuarioRepository.findAll().stream().map(usuarioMapper::toDomain).toList();
    }
}
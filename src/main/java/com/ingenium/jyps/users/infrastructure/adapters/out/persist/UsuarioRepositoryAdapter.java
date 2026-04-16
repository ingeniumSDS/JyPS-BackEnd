package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.domain.repository.UsuarioRepositoryPort;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.repository.JpaUsuarioRepository;
import com.ingenium.jyps.users.infrastructure.adapters.out.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpaUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity uE = usuarioMapper.toEntity(usuario);
        UsuarioEntity entidadGuardada = jpaUsuarioRepository.save(uE);
        return usuarioMapper.toDomain(entidadGuardada);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return jpaUsuarioRepository.findByCorreo(correo).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorTelefono(String telefono) {
        return jpaUsuarioRepository.findByTelefono(telefono).map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorToken(String token) {
        return jpaUsuarioRepository.findByCuenta_TokenRecuperacion(token).map(usuarioMapper::toDomain);
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
    public long contarPorDepartamento(Long id) {
        return jpaUsuarioRepository.countByDepartamento_Id(id);
    }

    @Override
    public List<Usuario> filtrarPorDepartamento(Long idDepartamento) {
        List<UsuarioEntity> usuariosEntity = jpaUsuarioRepository.findByDepartamento_Id(idDepartamento);
        return usuariosEntity.stream().map(usuarioMapper::toDomain).toList();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaUsuarioRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return jpaUsuarioRepository.findAll().stream().map(usuarioMapper::toDomain).toList();
    }

    @Override
    public List<Usuario> buscarJefes() {
        List<UsuarioEntity> jefes = jpaUsuarioRepository.findByRoles(List.of(Roles.JEFE_DE_DEPARTAMENTO));
        return jefes.stream().map(usuarioMapper::toDomain).toList();
    }


}
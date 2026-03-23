package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.DepartamentoEntity;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.CuentaEmbeddable;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpaUsuarioRepository;


    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity uE = mapToEntity(usuario);
        // Se encarga de guardar o actualizar la entidad dependiendo si el ID es nulo o no.
        // Tomado de JpaRepository ya que JpaUsuarioRepository extiende de esta interfaz.
        UsuarioEntity entidadGuardada = jpaUsuarioRepository.save(uE);
        return mapToDomain(entidadGuardada);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return jpaUsuarioRepository.findByCorreo(correo).map(this::mapToDomain);
    }

    @Override
    public Optional<Usuario> findByTelefono(String telefono) {
        return jpaUsuarioRepository.findByTelefono(telefono).map(this::mapToDomain);
    }

    @Override
    public boolean estaActivo(String correo) {
        Usuario usuario = jpaUsuarioRepository.findByCorreo(correo).map(this::mapToDomain).orElseThrow( () -> new  RuntimeException("Usuario no encontrado"));
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
        return jpaUsuarioRepository.findByCorreo(telefono).isPresent();

    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaUsuarioRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaUsuarioRepository.findAll().stream().map(this::mapToDomain).toList();
    }

    private UsuarioEntity mapToEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();

        entity.setId(usuario.getId());
        entity.setNombre(usuario.getNombre());
        entity.setApellidoPaterno(usuario.getApellidoPaterno());
        entity.setApellidoMaterno(usuario.getApellidoMaterno());
        entity.setCorreo(usuario.getCorreo());
        entity.setTelefono(usuario.getTelefono());
        entity.setHoraEntrada(usuario.getHoraEntrada());
        entity.setHoraSalida(usuario.getHoraSalida());
        entity.setRoles(usuario.getRoles());

        if (usuario.getDepartamentoId() != null) {
            DepartamentoEntity depEntity = new DepartamentoEntity();
            depEntity.setId(usuario.getDepartamentoId());
            entity.setDepartamento(depEntity);
        }

        if (usuario.getCuenta() != null) {
            CuentaEmbeddable cuentaEmb = getCuentaEmbeddable(usuario);
            entity.setCuenta(cuentaEmb);
        }

        return entity;
    }

    private Usuario mapToDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        Cuenta cuentaDominio = getCuenta(entity);

        Long depId = null;
        if (entity.getDepartamento() != null) {
            depId = entity.getDepartamento().getId();
        }

        return new Usuario(
                entity.getId(),
                entity.getNombre(),
                entity.getApellidoPaterno(),
                entity.getApellidoMaterno(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getHoraEntrada(),
                entity.getHoraSalida(),
                entity.getRoles(),
                depId, // <-- Le pasamos solo el ID
                cuentaDominio
        );
    }

    private static @NonNull CuentaEmbeddable getCuentaEmbeddable(Usuario usuario) {
        CuentaEmbeddable cuentaEmb = new CuentaEmbeddable();
        cuentaEmb.setPassword(usuario.getCuenta().getPassword());
        cuentaEmb.setIntentosFallidos(usuario.getCuenta().getIntentosFallidos());
        cuentaEmb.setTokenRecuperacion(usuario.getCuenta().getTokenRecuperacion());
        cuentaEmb.setTokenExpiresAt(usuario.getCuenta().getTokenExpiresAt());
        cuentaEmb.setTokenUsado(usuario.getCuenta().isTokenUsado());
        cuentaEmb.setBloqueada(usuario.getCuenta().isBloqueada());
        cuentaEmb.setActiva(usuario.getCuenta().isActiva());
        cuentaEmb.setBlockedAt(usuario.getCuenta().getBlockedAt());
        return cuentaEmb;
    }

    private static @Nullable Cuenta getCuenta(UsuarioEntity entity) {
        Cuenta cuentaDominio = null;
        if (entity.getCuenta() != null) {
            cuentaDominio = new Cuenta(
                    entity.getCuenta().getPassword(),
                    entity.getCuenta().getIntentosFallidos(),
                    entity.getCuenta().getTokenRecuperacion(),
                    entity.getCuenta().getTokenExpiresAt(),
                    entity.getCuenta().isTokenUsado(),
                    entity.getCuenta().isBloqueada(),
                    entity.getCuenta().isActiva(),
                    entity.getCuenta().getBlockedAt()
            );
        }
        return cuentaDominio;
    }
}
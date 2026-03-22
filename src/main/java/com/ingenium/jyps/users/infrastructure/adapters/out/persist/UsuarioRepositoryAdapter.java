package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.DepartamentoEntity;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepository;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.CuentaEmbeddable;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final SpringDataUsuarioRepository springRepository;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity u = mapToEntity(usuario);
        UsuarioEntity entidadGuardada = springRepository.save(u);
        return mapToDomain(entidadGuardada);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return springRepository.findByCorreo(correo).map(this::mapToDomain);
    }

    @Override
    public Optional<Usuario> findByTelefono(String telefono) {
        return Optional.empty(); // Ojo: falta implementarlo en SpringDataUsuarioRepository
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return springRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return springRepository.findAll().stream().map(this::mapToDomain).toList();
    }

    @Override
    public boolean deleteById(String email) {
        return false; // Ojo: falta implementar
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
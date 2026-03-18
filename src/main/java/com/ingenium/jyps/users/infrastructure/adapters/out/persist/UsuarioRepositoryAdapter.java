package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.DepartamentoEntity;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository repository;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity u = mapToEntity(usuario);
        UsuarioEntity entidadGuardada = repository.save(u); // ¡Aquí Spring ya le puso el ID!
        return mapToDomain(entidadGuardada); // Lo devolvemos al dominio con todo y ID
    }


    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        // Busca en BD y si lo encuentra, lo traduce al Dominio usando tu método mapToDomain
        return repository.findByCorreo(correo).map(this::mapToDomain);
    }

    @Override
    public Optional<Usuario> findByTelefono(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
       return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public boolean deleteById(String email) {
        return false;
    }

    // EL TRADUCTOR: De Dominio a Base de Datos
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
        // Si el usuario trae un departamento, lo convertimos a Entidad para Hibernate
        if (usuario.getDepartamento() != null) {
            DepartamentoEntity depEntity = new DepartamentoEntity();
            depEntity.setId(usuario.getDepartamento().getId());
            depEntity.setNombre(usuario.getDepartamento().getNombre());
            depEntity.setDescripcion(usuario.getDepartamento().getDescripcion());
            depEntity.setActivo(usuario.getDepartamento().isActivo());
            entity.setDepartamento(depEntity);
        }


        if (usuario.getCuenta() != null) {
            CuentaEmbeddable cuentaEmb = getCuentaEmbeddable(usuario);

            // Asigna el embeddable ya lleno a la entidad principal
            entity.setCuenta(cuentaEmb);
        }

        return entity;
    }

    private static @NonNull CuentaEmbeddable getCuentaEmbeddable(Usuario usuario) {
        CuentaEmbeddable cuentaEmb = new CuentaEmbeddable();

        // Extraemos los datos del dominio (usuario.getCuenta()) y los pasamos al Embeddable
        cuentaEmb.setPassword(usuario.getCuenta().getPassword());
        cuentaEmb.setIntentosFallidos(usuario.getCuenta().getIntentosFallidos());
        cuentaEmb.setTokenRecuperacion(usuario.getCuenta().getTokenRecuperacion());
        cuentaEmb.setTokenExpiresAt(usuario.getCuenta().getTokenExpiresAt());

        // Ojo: Lombok suele generar "is" en lugar de "get" para los booleanos
        cuentaEmb.setTokenUsado(usuario.getCuenta().isTokenUsado());
        cuentaEmb.setBloqueada(usuario.getCuenta().isBloqueada());
        cuentaEmb.setActiva(usuario.getCuenta().isActiva());
        cuentaEmb.setBlockedAt(usuario.getCuenta().getBlockedAt());
        return cuentaEmb;
    }

    private Usuario mapToDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        // 1. Reconstruimos la cuenta (Si existe)
        Cuenta cuentaDominio = getCuenta(entity);

        // Si la entidad trae un departamento de Oracle, lo convertimos a Dominio
        Departamento depDominio = null;

        if (entity.getDepartamento() != null) {
            depDominio = new Departamento(
                    entity.getDepartamento().getId(),
                    entity.getDepartamento().getNombre(),
                    entity.getDepartamento().getDescripcion(),
                    entity.getDepartamento().isActivo()
            );
        }

        // Y se lo pasas a tu constructor de rehidratación de Usuario

        // 2. Reconstruimos el usuario completo usando el constructor de rehidratación
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
                depDominio,
                cuentaDominio // Le pasamos la cuenta ya armada
        );
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
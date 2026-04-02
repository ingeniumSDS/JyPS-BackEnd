package com.ingenium.jyps.utils.mappers;

import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.DepartamentoEntity;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.CuentaEmbeddable;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioEntity toEntity(Usuario usuario) {
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

    public Usuario toDomain(UsuarioEntity entity) {
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
        cuentaEmb.setTokenRecuperacion(usuario.getCuenta().getTokenAcceso());
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

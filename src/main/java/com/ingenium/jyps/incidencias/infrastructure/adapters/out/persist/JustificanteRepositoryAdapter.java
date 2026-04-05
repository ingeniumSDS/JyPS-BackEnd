package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.departamentos.domain.model.Departamento;
import com.ingenium.jyps.departamentos.domain.ports.out.DepartamentoRepositoryPort;
import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.entity.DepartamentoEntity;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.application.ports.JustificanteRepositoryPort;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.repository.JpaJustificanteRepositoy;
import com.ingenium.jyps.users.application.ports.out.UsuarioRepositoryPort;
import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.CuentaEmbeddable;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JustificanteRepositoryAdapter implements JustificanteRepositoryPort {

    private final JpaJustificanteRepositoy jpaJustificanteRepositoy;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final DepartamentoRepositoryPort departamentoRepositoryPort;

    @Override
    public Justificante solicitar(Justificante justificante) {
        JustificanteEntity justificanteEntity = new JustificanteEntity();
        jpaJustificanteRepositoy.save(justificanteEntity);
        return justificante;
    }

    private JustificanteEntity mapToEntity(Justificante justificante) {

        Usuario u = usuarioRepositoryPort.buscarPorId(justificante.getEmpleadoId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Departamento d =departamentoRepositoryPort.buscarPorId(u.getDepartamentoId()).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        Long idJefe = d.getJefeId();
        Usuario jefe = usuarioRepositoryPort.buscarPorId(idJefe).orElseThrow(() -> new RuntimeException("Jefe no encontrado"));

        JustificanteEntity justificanteEntity = new JustificanteEntity();
        justificanteEntity.setId(justificante.getId());
        justificanteEntity.setEmpleado(mapUserToEntity(u));
        justificanteEntity.setJefe(mapUserToEntity(jefe));
        justificanteEntity.setDetalles(justificante.getDescripcion());
        justificanteEntity.setFechaSolicitada(justificante.getFechaSolicitada());
        justificanteEntity.setFechaSolicitud(justificante.getFechaSolicitud());
        justificanteEntity.setEstado(justificante.getEstado());
        justificanteEntity.setArchivos(justificante.getArchivos());

        return null;
    }

    private UsuarioEntity mapUserToEntity(Usuario usuario) {
        Usuario u = usuarioRepositoryPort.buscarPorId(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        DepartamentoEntity departamentoEntity = new DepartamentoEntity();
        departamentoEntity.setId(u.getDepartamentoId());

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(u.getId());
        usuarioEntity.setNombre(u.getNombre());
        usuarioEntity.setApellidoPaterno(u.getApellidoPaterno());
        usuarioEntity.setApellidoMaterno(u.getApellidoMaterno());
        usuarioEntity.setCorreo(u.getCorreo());
        usuarioEntity.setTelefono(u.getTelefono());
        usuarioEntity.setHoraEntrada(u.getHoraEntrada());
        usuarioEntity.setHoraSalida(u.getHoraSalida());
        usuarioEntity.setRoles(u.getRoles());

        if (usuario.getDepartamentoId() != null) {
            DepartamentoEntity depEntity = new DepartamentoEntity();
            depEntity.setId(usuario.getDepartamentoId());
            usuarioEntity.setDepartamento(depEntity);
        }

        if (usuario.getCuenta() != null) {
            CuentaEmbeddable cuentaEmb = getCuentaEmbeddable(usuario);
            usuarioEntity.setCuenta(cuentaEmb);
        }

        return usuarioEntity;

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

    private Usuario mapUsuarioToDomain(UsuarioEntity entity) {
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

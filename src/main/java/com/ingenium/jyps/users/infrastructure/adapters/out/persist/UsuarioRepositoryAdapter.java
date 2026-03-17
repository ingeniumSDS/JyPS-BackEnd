package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import com.ingenium.jyps.users.domain.model.Cuenta;
import com.ingenium.jyps.users.domain.model.Usuario;
import com.ingenium.jyps.users.domain.ports.out.PasswordEncoderPort;
import com.ingenium.jyps.users.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository repository;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Usuario usuario) {

        UsuarioEntity uEntity = mapToEntity(usuario);
        repository.save(uEntity);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        // Esto lo implementaremos después, ¡enfócate en el save!
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findByTelefono(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.empty();
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


        if (usuario.getCuenta() != null) {
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

            // Asigna el embeddable ya lleno a la entidad principal
            entity.setCuenta(cuentaEmb);
        }

        return entity;
    }

    private Usuario mapToDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        // 1. Reconstruimos la cuenta (Si existe)
        Cuenta cuentaDominio = null;
        if (entity.getCuenta() != null) {
            cuentaDominio = new Cuenta(
                    entity.getCuenta().getPassword(),
                    entity.getCuenta().getIntentosFallidos(),
                    entity.getCuenta().getTokenRecuperacion(),
                    entity.getCuenta().isTokenUsado(),
                    entity.getCuenta().isBloqueada(),
                    entity.getCuenta().isActiva(),
                    entity.getCuenta().getBlockedAt()
            );
        }

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
                cuentaDominio // Le pasamos la cuenta ya armada
        );
    }

}
package com.ingenium.jyps.users.infrastructure.adapters.out.persist;

import aj.org.objectweb.asm.commons.Remapper;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByTelefono(String telefono);
    Optional<UsuarioEntity> findByCuenta_TokenRecuperacion(String cuentaTokenRecuperacion);
}
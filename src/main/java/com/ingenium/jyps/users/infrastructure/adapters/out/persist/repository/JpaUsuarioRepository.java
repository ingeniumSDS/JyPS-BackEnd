package com.ingenium.jyps.users.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByTelefono(String telefono);
    Optional<UsuarioEntity> findByCuenta_TokenRecuperacion(String cuentaTokenRecuperacion);

    Long countByDepartamento_Id(Long departamentoId);
    List<UsuarioEntity> findByDepartamento_Id(Long departamentoId);
}
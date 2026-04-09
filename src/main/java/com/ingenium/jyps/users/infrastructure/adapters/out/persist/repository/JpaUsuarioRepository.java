package com.ingenium.jyps.users.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.users.domain.model.enums.Roles;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByTelefono(String telefono);
    Optional<UsuarioEntity> findByCuenta_TokenRecuperacion(String cuentaTokenRecuperacion);

    Long countByDepartamento_Id(Long departamentoId);
    
    List<UsuarioEntity> findByDepartamento_Id(Long departamentoId);

    @Query(value = "select u from UsuarioEntity u join u.roles r where r in :roles")
    List<UsuarioEntity> findByRoles(List<Roles> roles);

}
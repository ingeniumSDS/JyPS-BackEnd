package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.repository;

import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.entity.DepartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDepartamentoRepository extends JpaRepository<DepartamentoEntity, Long> {

    Optional<DepartamentoEntity> findByNombre(String nombre);

    Optional<DepartamentoEntity> findByJefeId(Long jefeId);
}

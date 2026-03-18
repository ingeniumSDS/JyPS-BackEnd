package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataDepartamentoRepository extends JpaRepository<DepartamentoEntity, Long> {



    Optional<DepartamentoEntity> findByNombre(String nombre);

}

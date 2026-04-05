package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.entity;

import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "departamentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false, unique = true)
    private String nombre;
    private String descripcion;
    private boolean activo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", foreignKey = @ForeignKey(name = "FK_DEPARTAMENTO_JEFE"))
    private UsuarioEntity jefe;

}

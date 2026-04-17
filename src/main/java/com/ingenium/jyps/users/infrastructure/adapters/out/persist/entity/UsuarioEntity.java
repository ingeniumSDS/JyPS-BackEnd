package com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity;


import com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.entity.DepartamentoEntity;
import com.ingenium.jyps.users.domain.model.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidoPaterno;
    private String apellidoMaterno;
    @Column(unique = true, nullable = false)
    private String correo;
    @Column(nullable = false)
    private String telefono;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), foreignKey = @ForeignKey(name = "FK_ROLES_USAURIO"))
    private List<Roles> roles;
    @ManyToOne(fetch = FetchType.LAZY) // LAZY es buena práctica para no saturar la memoria
    @JoinColumn(name = "departamento_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USUARIO_DEPARTAMENTO"))
    private DepartamentoEntity departamento;

    @Embedded
    private CuentaEmbeddable cuenta;

}

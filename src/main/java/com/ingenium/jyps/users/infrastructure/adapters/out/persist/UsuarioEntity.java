package com.ingenium.jyps.users.infrastructure.adapters.out.persist;


import com.ingenium.jyps.users.domain.model.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Data
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
    @Column(nullable = false)
    private String apellidoMaterno;
    @Column(unique = true, nullable = false)
    private String correo;
    @Column(nullable = false)
    private String telefono;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    private List<Roles> roles;

    @Embedded
    private CuentaEmbeddable cuenta;

}

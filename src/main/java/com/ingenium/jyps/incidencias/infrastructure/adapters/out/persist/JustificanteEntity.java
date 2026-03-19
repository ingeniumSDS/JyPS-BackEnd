package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "justificantes")
public class JustificanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    private UsuarioEntity empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", nullable = false)
    private UsuarioEntity jefe;

    @Column(name = "fecha_solicitada", nullable = false)
    private LocalDate fechaSolicitada;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "detalles", length = 500)
    private String detalles;

    @Column(name = "archivos")
    private String archivos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosIncidencia estado;

}
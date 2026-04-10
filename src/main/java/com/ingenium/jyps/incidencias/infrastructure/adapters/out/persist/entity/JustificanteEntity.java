package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @JoinColumn(name = "id_empleado", nullable = false, foreignKey = @ForeignKey(name = "FK_JUSTIFICANTE_EMPLEADO"))
    private UsuarioEntity empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", nullable = false, foreignKey = @ForeignKey(name = "FK_JUSTIFICANTE_JEFE"))
    private UsuarioEntity jefe;

    @Column(name = "fecha_solicitada", nullable = false)
    private LocalDate fechaSolicitada;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "detalles", length = 500)
    private String detalles;

    @Column(name = "archivos")
    @ElementCollection
    @CollectionTable(name = "justificante_evidencias", joinColumns = @JoinColumn(name = "justificante_id"), foreignKey = @ForeignKey(name = "FK_JUSTIFICANTE_ARCHIVOS"))
    private List<String> archivos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosIncidencia estado;

    @Column(name = "comentario")
    private String comentario;

}
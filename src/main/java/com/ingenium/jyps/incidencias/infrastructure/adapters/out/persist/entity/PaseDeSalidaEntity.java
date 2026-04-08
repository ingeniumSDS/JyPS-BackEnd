package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity;

import com.ingenium.jyps.incidencias.domain.model.enums.EstadosIncidencia;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pases_de_salida")
public class PaseDeSalidaEntity {

    //========================//
    // DATOS DE IDENTIFICACIÓN//
    //========================//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false, foreignKey = @ForeignKey(name = "FK_PASE_EMPLEADO"))
    private UsuarioEntity empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", nullable = false, foreignKey = @ForeignKey(name = "FK_PASE_JEFE"))
    private UsuarioEntity jefe;

    @Column(name = "hora_solicitada", nullable = false)
    private LocalDateTime horaSolicitada;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "hora_salida_real")
    private LocalDateTime horaSalidaReal;


    @Column(name = "hora_esperada", nullable = false)
    private LocalDateTime horaEsperada;

    @Column(name = "detalles", length = 500)
    private String detalles;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosIncidencia estado;

    @Column(name = "codigo_qr")
    private String QR;

    @Column(name = "archivos")
    @ElementCollection
    @CollectionTable(name = "pase_de_salida_evidencias", joinColumns = @JoinColumn(name = "justificante_id"), foreignKey = @ForeignKey(name = "FK_JUSTIFICANTE_ARCHIVOS"))
    private List<String> archivos;

}
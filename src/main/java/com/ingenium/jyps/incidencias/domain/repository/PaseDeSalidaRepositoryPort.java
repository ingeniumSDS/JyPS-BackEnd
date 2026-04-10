package com.ingenium.jyps.incidencias.domain.repository;

import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;

import java.util.List;


public interface PaseDeSalidaRepositoryPort {

    PaseDeSalida solicitar(PaseDeSalida paseDeSalida);

    boolean solicitudEnCurso();

    PaseDeSalida buscarPorId(long paseDeSalidaId);

    List<PaseDeSalida> buscarPorIdEmpleado(Long empleadoId);

    List<PaseDeSalida> buscarPorIdJefe(Long jefeId);

    PaseDeSalida buscarPorQR(String qr);
}

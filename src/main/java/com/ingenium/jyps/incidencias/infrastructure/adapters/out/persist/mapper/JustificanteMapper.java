package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.JustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JustificanteMapper {

    @Mapping(target = "descripcion", source = "justificanteEntity.detalles")
    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "jefeId", source = "jefe.id")
    Justificante toDomain(JustificanteEntity justificanteEntity);

    @InheritInverseConfiguration
    JustificanteEntity toEntity(Justificante justificante);

    SolicitarJustificanteCommand toCommand(JustificanteRequest request);

    JustificanteResponse toResponse(Justificante justificante);

}

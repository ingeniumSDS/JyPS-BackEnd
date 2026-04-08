package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.ArchivoAdjunto;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.PaseDeSalidaResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaseDeSalidaMapper {

    @Mapping(target = "descripcion", source = "paseDeSalidaEntity.detalles")
    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "jefeId", source = "jefe.id")
    PaseDeSalida toDomain(PaseDeSalidaEntity paseDeSalidaEntity);

    @InheritInverseConfiguration
    PaseDeSalidaEntity toEntity(PaseDeSalida paseDeSalida);

    @Mapping(target = "archivos", source = "archivos")
    SolicitarPaseDeSalidaCommand toCommand(SolicitarPaseDeSalidaRequest request, List<MultipartFile> archivos);
    default ArchivoAdjunto mapMultipartToArchivoAdjunto(MultipartFile file) {

        if (file == null || file.isEmpty()) return null;

        try {
            return new ArchivoAdjunto(
                    file.getOriginalFilename(), // Aquí rescatamos el nombre que viste en el debugger
                    file.getContentType(),      // El tipo (application/pdf)
                    file.getBytes()             // Los bytes reales
            );
        } catch (IOException e) {
            // Es vital manejar la excepción porque getBytes() la lanza
            throw new RuntimeException("No se pudieron leer los bytes del archivo: " + file.getOriginalFilename(), e);
        }
    }

    PaseDeSalidaResponse toResponse(PaseDeSalida paseDeSalida);


}

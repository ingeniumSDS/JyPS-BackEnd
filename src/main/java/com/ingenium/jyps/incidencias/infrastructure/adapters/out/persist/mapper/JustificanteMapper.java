package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.ArchivoAdjunto;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JustificanteMapper {

    @Mapping(target = "descripcion", source = "justificanteEntity.detalles")
    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "jefeId", source = "jefe.id")
    Justificante toDomain(JustificanteEntity justificanteEntity);

    @InheritInverseConfiguration
    JustificanteEntity toEntity(Justificante justificante);

    @Mapping(target = "archivos", source = "archivos")
    SolicitarJustificanteCommand toCommand(SolicitarJustificanteRequest request, List<MultipartFile> archivos);
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

    JustificanteResponse toResponse(Justificante justificante);

}

package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.ArchivoAdjunto;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.RevisarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.paseDeSalida.SolicitarPaseDeSalidaCommand;
import com.ingenium.jyps.incidencias.domain.model.PaseDeSalida;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RangoDeFechasRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarPaseDeSalidaRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.PaseDeSalidaResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.PaseDeSalidaEntity;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import jakarta.validation.Valid;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface PaseDeSalidaMapper {

    @Mapping(target = "descripcion", source = "paseDeSalidaEntity.detalles")
    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "jefeId", source = "jefe.id")
    @Mapping(target = "nombreCompletoEmpleado", expression = "java(mapNombreCompleto(paseDeSalidaEntity.getEmpleado()))")
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

    @Mapping(target = "nombreCompleto", source = "nombreCompleto")
    PaseDeSalidaResponse toResponse(PaseDeSalida paseDeSalida);

    default String mapNombreCompleto(UsuarioEntity empleado) {
        if (empleado == null) {
            return null;
        }
        StringBuilder nombre = new StringBuilder();

        if (tieneTexto(empleado.getNombre())) {
            nombre.append(empleado.getNombre().trim());
        }
        if (tieneTexto(empleado.getApellidoPaterno())) {
            if (!nombre.isEmpty()) nombre.append(" ");
            nombre.append(empleado.getApellidoPaterno().trim());
        }
        if (tieneTexto(empleado.getApellidoMaterno())) {
            if (!nombre.isEmpty()) nombre.append(" ");
            nombre.append(empleado.getApellidoMaterno().trim());
        }

        return nombre.isEmpty() ? null : nombre.toString();
    }

    default boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }



    RevisarPaseDeSalidaCommand toRevisarPaseDeSalidaCommand(RevisarPaseDeSalidaRequest request);

    RangoDeFechasCommand toRangoDeFechasCommand(RangoDeFechasRequest request);
}

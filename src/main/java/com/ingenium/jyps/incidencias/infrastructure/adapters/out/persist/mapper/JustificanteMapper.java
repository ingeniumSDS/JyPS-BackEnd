package com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.incidencias.domain.model.ArchivoAdjunto;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.RangoDeFechasCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.RevisarJustificanteCommand;
import com.ingenium.jyps.incidencias.application.ports.in.usecases.command.justificante.SolicitarJustificanteCommand;
import com.ingenium.jyps.incidencias.domain.model.Justificante;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RangoDeFechasRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.RevisarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.request.SolicitarJustificanteRequest;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.JustificanteResponse;
import com.ingenium.jyps.incidencias.infrastructure.adapters.in.web.dto.response.ArchivoResponse; // Necesitas crear este DTO
import com.ingenium.jyps.incidencias.infrastructure.adapters.out.persist.entity.JustificanteEntity;
import com.ingenium.jyps.users.infrastructure.adapters.out.persist.entity.UsuarioEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class JustificanteMapper {

    @Mapping(target = "descripcion", source = "justificanteEntity.detalles")
    @Mapping(target = "empleadoId", source = "empleado.id")
    @Mapping(target = "jefeId", source = "jefe.id")
    @Mapping(target = "nombreCompleto", expression = "java(mapNombreCompleto(justificanteEntity.getEmpleado()))")
    public abstract Justificante toDomain(JustificanteEntity justificanteEntity);

    @InheritInverseConfiguration
    public abstract JustificanteEntity toEntity(Justificante justificante);

    @Mapping(target = "archivos", source = "archivos")
    public abstract SolicitarJustificanteCommand toCommand(SolicitarJustificanteRequest request, List<MultipartFile> archivos);

    /**
     * Mapeo personalizado para convertir la lista de nombres de archivos
     * en una lista de DTOs con URLs de descarga.
     */
    @Mapping(target = "archivos", expression = "java(mapNombresToUrls(justificante))")
    public abstract JustificanteResponse toResponse(Justificante justificante);

    protected List<ArchivoResponse> mapNombresToUrls(Justificante justificante) {
        if (justificante.getArchivos() == null || justificante.getArchivos().isEmpty()) {
            return Collections.emptyList();
        }

        return justificante.getArchivos().stream()
                .map(nombreUnico -> {
                    // Genera la URL dinámica: http://host:port/api/v1/justificantes/{id}/archivos/{nombre}
                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .scheme("https")
                            .path("/api/v1/justificantes/")
                            .path(justificante.getEmpleadoId().toString())
                            .path("/")
                            .path(nombreUnico)
                            .toUriString();

                    return new ArchivoResponse(extraerNombreOriginal(nombreUnico), url);
                })
                .collect(Collectors.toList());
    }

    private String extraerNombreOriginal(String nombreUnico) {
        return nombreUnico.contains("_") ? nombreUnico.substring(nombreUnico.indexOf("_") + 1) : nombreUnico;
    }

    // --- Métodos de utilidad existentes ---

    public ArchivoAdjunto mapMultipartToArchivoAdjunto(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            return new ArchivoAdjunto(file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al leer bytes del archivo", e);
        }
    }

    public String mapNombreCompleto(UsuarioEntity empleado) {
        if (empleado == null) return null;
        StringBuilder nombre = new StringBuilder();
        if (tieneTexto(empleado.getNombre())) nombre.append(empleado.getNombre().trim());
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

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    public abstract RevisarJustificanteCommand toRevisarJustificanteCommand(RevisarJustificanteRequest request);
    public abstract RangoDeFechasCommand toRangoDeFechasCommand(RangoDeFechasRequest request);
}
package com.ingenium.jyps.users.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.users.application.ports.in.usecases.command.RegistrarUsuarioCommand;
import com.ingenium.jyps.users.infrastructure.adapters.in.web.dto.request.CrearUsuarioRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    RegistrarUsuarioCommand toCommand(CrearUsuarioRequest crearUsuarioRequest);


}

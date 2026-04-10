package com.ingenium.jyps.departamentos.infrastructure.adapters.out.persist.mapper;

import com.ingenium.jyps.departamentos.application.ports.in.command.CrearDepartamentoCommand;
import com.ingenium.jyps.departamentos.application.ports.in.command.UpdateDepartamentoCommand;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request.CrearDepartamentoRequest;
import com.ingenium.jyps.departamentos.infrastructure.adapters.in.web.dto.request.UpdateDepartamentoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    CrearDepartamentoCommand toCreateCommand(CrearDepartamentoRequest request);

    UpdateDepartamentoCommand toUpdateCommand(UpdateDepartamentoRequest request);



}



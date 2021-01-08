package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ModuleEntity;
import com.esprit.dto.request.CreateModuleRequest;
import com.esprit.dto.request.UpdateModuleRequest;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.TeacherRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

	ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

	ModuleEntity createModuleRequestToModuleEntity(CreateModuleRequest createModuleRequest,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository);

	ModuleEntity updateModuleRequestToModuleEntity(UpdateModuleRequest updateModuleRequest,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository);

	ModuleResponse moduleEntityToModuleResponse(ModuleEntity moduleEntity);

	List<ModuleResponse> moduleEntitiesToModuleResponse(List<ModuleEntity> moduleEntities);

}
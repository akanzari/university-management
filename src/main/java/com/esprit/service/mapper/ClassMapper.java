package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.classes.CreateClassRequest;
import com.esprit.service.SpecialityService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { SpecialityService.class })
public interface ClassMapper {

	ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

	@Mapping(source = "speciality", target = "speciality", qualifiedByName = "findSpeciality")
	ClassEntity createClassRequestToClassEntity(CreateClassRequest createClassRequest);

	@Mapping(target = "module", ignore = true)
	@Mapping(target = "teachers", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	ClassDTO classEntityToClassDTO(ClassEntity classEntity);

	List<ClassDTO> classEntitiesToClassDTO(List<ClassEntity> classEntities);

}
package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.ClassDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassMapper {

	ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

	ClassEntity classDTOToClassEntity(ClassDTO classDTO);

	ClassDTO classEntityToClassDTO(ClassEntity classEntity);

	List<ClassDTO> classEntitiesToClassDTO(List<ClassEntity> classEntities);

}
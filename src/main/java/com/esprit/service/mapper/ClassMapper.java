package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ClassEntity;
import com.esprit.dto.request.CreateClassRequest;
import com.esprit.dto.request.UpdateClassRequest;
import com.esprit.dto.response.ClassResponse;
import com.esprit.repository.SpecialityRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassMapper {

	ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

	ClassEntity createClassRequestRequestToClassEntity(CreateClassRequest createClassRequest,
			@Context SpecialityRepository specialityRepository);

	ClassEntity updateClassRequestRequestToClassEntity(UpdateClassRequest updateClassRequest,
			@Context SpecialityRepository specialityRepository);

	ClassResponse classEntityToClassResponse(ClassEntity classEntity);

	List<ClassResponse> classEntitiesToClassResponse(List<ClassEntity> classEntities);

	@AfterMapping
	static void after(CreateClassRequest source, @MappingTarget ClassEntity target,
			@Context SpecialityRepository specialityRepository) {
		target.setSpeciality(specialityRepository.getOne(source.getSpecialityId()));
	}

	@AfterMapping
	static void after(UpdateClassRequest source, @MappingTarget ClassEntity target,
			@Context SpecialityRepository specialityRepository) {
		target.setSpeciality(specialityRepository.getOne(source.getSpecialityId()));
	}

}
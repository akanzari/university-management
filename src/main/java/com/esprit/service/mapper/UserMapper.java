package com.esprit.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.esprit.domain.StudentEntity;
import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.response.StudentResponse;
import com.esprit.repository.ClassRepository;

@Mapper
public interface UserMapper {

	@Mapping(target = "fullName", qualifiedByName = "IAMService, findFullNameById")
	StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest,
			@Context ClassRepository classRepository);

	StudentResponse studentEntityToStudentResponse(StudentEntity studentEntity);

	@AfterMapping
	static void after(CreateStudentRequest source, @MappingTarget StudentEntity target,
			@Context ClassRepository classRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
	}

}
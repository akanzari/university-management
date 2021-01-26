package com.esprit.service.mapper;

import java.util.List;

import org.keycloak.representations.AccessToken;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.esprit.domain.ClassEntity;
import com.esprit.domain.StudentEntity;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.student.CreateStudentRequest;
import com.esprit.dto.student.StudentDTO;
import com.esprit.repository.ClassRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

	StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest,
			@Context ClassRepository classRepository);

	StudentDTO studentEntityToStudentDTO(StudentEntity studentEntity);

	@Mapping(target = "teachers", ignore = true)
	@Mapping(target = "rooms", ignore = true)
	@Mapping(target = "module", ignore = true)
	ClassDTO classEntityToClassDTO(ClassEntity classEntity);

	List<StudentDTO> studentEntitiesToStudentDTO(List<StudentEntity> studentEntity);

	@AfterMapping
	static void setFullName(StudentDTO source, @MappingTarget AccessToken target) {
		source.setFullName(target.getGivenName() + " " + target.getFamilyName());
	}

	@AfterMapping
	static void after(CreateStudentRequest source, @MappingTarget StudentEntity target,
			@Context ClassRepository classRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
	}

}
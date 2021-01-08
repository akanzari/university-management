package com.esprit.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.StudentEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.CreateStudentRequest;
import com.esprit.dto.request.CreateTeacherRequest;
import com.esprit.dto.response.StudentResponse;
import com.esprit.dto.response.TeacherResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.DepartmentRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	StudentEntity createStudentRequestToStudentEntity(CreateStudentRequest createStudentRequest,
			@Context ClassRepository classRepository);

	StudentResponse studentEntityToStudentResponse(StudentEntity studentEntity);

	TeacherEntity createTeacherRequestoTeacherEntity(CreateTeacherRequest createTeacherRequest,
			@Context DepartmentRepository departmentRepository);

	TeacherResponse teacherEntityToTeacherResponse(TeacherEntity teacherEntity);

	@AfterMapping
	static void after(CreateStudentRequest source, @MappingTarget StudentEntity target,
			@Context ClassRepository classRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
	}

	@AfterMapping
	static void after(CreateTeacherRequest source, @MappingTarget TeacherEntity target,
			@Context DepartmentRepository departmentRepository) {
		target.setDepartment(departmentRepository.getOne(source.getDepartmentId()));
	}

}
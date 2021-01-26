package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.TeacherEntity;
import com.esprit.dto.teacher.CreateTeacherRequest;
import com.esprit.dto.teacher.TeacherDTO;
import com.esprit.service.DepartmentService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { DepartmentService.class, DisponibilityMapper.class })
public interface TeacherMapper {

	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

	@Mapping(source = "departement", target = "departement", qualifiedByName = "findDepartment")
	TeacherEntity createTeacherRequestToTeacherEntity(CreateTeacherRequest createTeacherRequest);

	TeacherDTO teacherEntityToTeacherDTO(TeacherEntity teacherEntity);

	List<TeacherDTO> teacherEntitiesToTeacherDTO(List<TeacherEntity> teacherEntities);

}
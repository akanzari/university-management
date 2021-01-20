package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassExamEntity;
import com.esprit.domain.ExamEntity;
import com.esprit.dto.exam.CreateAssignClassExamRequest;
import com.esprit.dto.exam.CreateExamRequest;
import com.esprit.service.ClassService;
import com.esprit.service.ModuleService;
import com.esprit.service.RoomService;
import com.esprit.service.TeacherService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TeacherService.class, RoomService.class,
		ClassService.class, ModuleService.class })
public interface ExamMapper {

	ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

	ExamEntity createExamRequestToExamEntity(CreateExamRequest createExamRequest);

	@Mapping(source = "teachers", target = "teachers", qualifiedByName = "findTeachersByIds")
	@Mapping(source = "rooms", target = "rooms", qualifiedByName = "findRoomsByIds")
	@Mapping(source = "classes", target = "classes", qualifiedByName = "findClassesByIds")
	@Mapping(source = "module", target = "module", qualifiedByName = "findModuleById")
	AssignClassExamEntity createAssignClassExamRequestToAssignClassExamEntity(
			CreateAssignClassExamRequest createAssignClassExamRequest);

	List<AssignClassExamEntity> createAssignClassExamRequestToAssignClassExamEntities(
			List<CreateAssignClassExamRequest> createAssignClassExamRequest);

}
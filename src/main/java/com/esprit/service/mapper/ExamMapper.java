package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ExamEntity;
import com.esprit.dto.request.exams.CreateExamRequest;
import com.esprit.dto.request.exams.UpdateExamRequest;
import com.esprit.dto.response.ExamResponse;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.RoomRepository;
import com.esprit.repository.ModuleRepository;
import com.esprit.repository.TeacherRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExamMapper {

	ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

	ExamEntity createExamRequestToExamEntity(CreateExamRequest createExamRequest,
			@Context ClassRepository classRepository, @Context ModuleRepository moduleRepository,
			@Context RoomRepository classRoomRepository, @Context TeacherRepository teacherRepository);

	ExamEntity updateExamRequestToExamEntity(UpdateExamRequest updateExamRequest,
			@Context ClassRepository classRepository, @Context ModuleRepository moduleRepository,
			@Context RoomRepository classRoomRepository, @Context TeacherRepository teacherRepository);

	ExamResponse examEntityToExamResponse(ExamEntity examEntity);

	List<ExamResponse> examEntitiessToExamResponse(List<ExamEntity> examEntities);

	@AfterMapping
	static void after(CreateExamRequest source, @MappingTarget ExamEntity target,
			@Context ClassRepository classRepository, @Context ModuleRepository moduleRepository,
			@Context RoomRepository classRoomRepository, @Context TeacherRepository teacherRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
		target.setModule(moduleRepository.getOne(source.getModuleId()));
		target.setClassRoom(classRoomRepository.getOne(source.getClassRoomId()));
		target.setSupervisor(teacherRepository.getOne(source.getSupervisorId()));
	}

	@AfterMapping
	static void after(UpdateExamRequest source, @MappingTarget ExamEntity target,
			@Context ClassRepository classRepository, @Context ModuleRepository moduleRepository,
			@Context RoomRepository classRoomRepository, @Context TeacherRepository teacherRepository) {
		target.setClasss(classRepository.getOne(source.getClassId()));
		target.setModule(moduleRepository.getOne(source.getModuleId()));
		target.setClassRoom(classRoomRepository.getOne(source.getClassRoomId()));
		target.setSupervisor(teacherRepository.getOne(source.getSupervisorId()));
	}

}
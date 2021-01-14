package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.request.modules.CreateModuleRequest;
import com.esprit.dto.response.ModuleResponse;
import com.esprit.enums.ExamTypeEnum;
import com.esprit.enums.PeriodEnum;
import com.esprit.enums.SemesterEnum;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.TeacherRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

	ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

	ModuleEntity createModuleRequestToModuleEntity(CreateModuleRequest createModuleRequest,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository);

	ModuleResponse moduleEntityToModuleResponse(ModuleEntity moduleEntity);

	List<ModuleResponse> moduleEntitiesToModuleResponse(List<ModuleEntity> moduleEntities);

	@AfterMapping
	static void after(CreateModuleRequest source, @MappingTarget ModuleEntity target,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository) {
		source.getAssignClasses().forEach(item -> {
			List<TeacherEntity> teacherEntities = new ArrayList<>();
			AssignClassEntity assignClassEntity = new AssignClassEntity();
			assignClassEntity.coefficient(item.getCoefficient()).nbrHours(item.getNbrHours())
					.semester(toSemesterEnum(item.getSemester())).periods(toPeriodEnum(item.getPeriods()))
					.examType(toExamTypeEnum(item.getExamType())).classs(classRepository.getOne(item.getClassId()));
			item.getTeacherIds().forEach(teacher -> teacherEntities.add(teacherRepository.getOne(teacher)));
			assignClassEntity.teachers(teacherEntities);
		});
	}

	/*
	 * @AfterMapping static void after(UpdateModuleRequest source, @MappingTarget
	 * ModuleEntity target,
	 * 
	 * @Context TeacherRepository teacherRepository, @Context ClassRepository
	 * classRepository) {
	 * target.setTeacher(teacherRepository.getOne(source.getTeacherId()));
	 * target.setClasss(classRepository.getOne(source.getClassId())); }
	 */

	public static SemesterEnum toSemesterEnum(final String semester) {
		return SemesterEnum.forValue(semester);
	}

	public static List<PeriodEnum> toPeriodEnum(final List<String> periods) {
		List<PeriodEnum> result = new ArrayList<>();
		periods.forEach(period -> result.add(PeriodEnum.forValue(period)));
		return result;
	}

	public static ExamTypeEnum toExamTypeEnum(final String examType) {
		return ExamTypeEnum.forValue(examType);
	}

	public default String toSemesterString(final SemesterEnum semester) {
		return SemesterEnum.forKey(semester);
	}

	public default List<String> toPeriodString(final List<PeriodEnum> periods) {
		List<String> result = new ArrayList<>();
		periods.forEach(period -> result.add(PeriodEnum.forKey(period)));
		return result;
	}

	public default String toExamTypeString(final ExamTypeEnum examType) {
		return ExamTypeEnum.forKey(examType);
	}

}
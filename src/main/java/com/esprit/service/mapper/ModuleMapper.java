package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassModuleEntity;
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

		List<AssignClassModuleEntity> assignClassEntities = new ArrayList<>();

		source.getAssignClasses().forEach(item -> {
			List<TeacherEntity> teacherEntities = new ArrayList<>();
			AssignClassModuleEntity assignClassEntity = new AssignClassModuleEntity();

			assignClassEntity.coefficient(item.getCoefficient()).nbrHour(item.getNbrHour())
					.semester(toSemesterEnum(item.getSemester())).period(toPeriodEnum(item.getPeriod()))
					.typeExam(toExamTypeEnum(item.getTypeExam()));

			if (classRepository.getOne(item.getClassId()) != null) {
				assignClassEntity.classs(classRepository.getOne(item.getClassId()));
			}

			if (CollectionUtils.isNotEmpty(item.getTeacherIds())) {
				item.getTeacherIds().forEach(teacher -> {
					if (!StringUtils.isBlank(teacher)) {
						if (!teacher.equals("0")) {
							teacherEntities.add(teacherRepository.getOne(teacher));
						}
					}
				});
				assignClassEntity.teachers(teacherEntities);
			}
			assignClassEntities.add(assignClassEntity);
		});
		target.setAssignClasses(assignClassEntities);

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

	public static PeriodEnum toPeriodEnum(final String period) {
		return PeriodEnum.forValue(period);
	}

	public static ExamTypeEnum toExamTypeEnum(final String examType) {
		return ExamTypeEnum.forValue(examType);
	}

	public default String toSemesterString(final SemesterEnum semester) {
		String result = null;
		if (semester != null) {
			result = SemesterEnum.forKey(semester);
		}
		return result;
	}

	public default List<String> toPeriodString(final List<PeriodEnum> periods) {
		List<String> result = new ArrayList<>();
		periods.forEach(period -> result.add(PeriodEnum.forKey(period)));
		return result;
	}

	public default String toExamTypeString(final ExamTypeEnum examType) {
		String result = null;
		if (examType != null) {
			result = ExamTypeEnum.forKey(examType);
		}
		return result;
	}

}
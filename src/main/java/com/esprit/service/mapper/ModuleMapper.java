package com.esprit.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassModuleEntity;
import com.esprit.domain.ClassEntity;
import com.esprit.domain.ModuleEntity;
import com.esprit.domain.TeacherEntity;
import com.esprit.dto.module.CreateModuleRequest;
import com.esprit.dto.module.ModuleDTO;
import com.esprit.dto.module.ModuleWithoutAssignClassesDTO;
import com.esprit.enums.ExamTypeEnum;
import com.esprit.repository.ClassRepository;
import com.esprit.repository.PeriodRepository;
import com.esprit.repository.SemesterRepository;
import com.esprit.repository.TeacherRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { SemesterMapper.class, AssignClassModuleMapper.class })
public interface ModuleMapper {

	ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

	ModuleEntity createModuleRequestToModuleEntity(CreateModuleRequest createModuleRequest,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository,
			@Context SemesterRepository semesterRepository, @Context PeriodRepository periodRepository);

	ModuleDTO moduleEntityToModuleDTO(ModuleEntity moduleEntity);
	
	ModuleDTO moduleWithoutAssignClassesDTOToModuleResponse(ModuleWithoutAssignClassesDTO moduleWithoutAssignClassesDTO);
	
	List<ModuleDTO> moduleWithoutAssignClassesDTOsToModuleResponse(List<ModuleWithoutAssignClassesDTO> moduleWithoutAssignClassesDTOs);

	List<ModuleDTO> moduleEntitiesToModuleDTO(List<ModuleEntity> moduleEntities);

	@AfterMapping
	static void after(CreateModuleRequest source, @MappingTarget ModuleEntity target,
			@Context TeacherRepository teacherRepository, @Context ClassRepository classRepository,
			@Context SemesterRepository semesterRepository, @Context PeriodRepository periodRepository) {

		List<AssignClassModuleEntity> assignClassEntities = new ArrayList<>();

		source.getAssignClasses().forEach(item -> {
			List<TeacherEntity> teacherEntities = new ArrayList<>();
			AssignClassModuleEntity assignClassEntity = new AssignClassModuleEntity();

			assignClassEntity.coefficient(item.getCoefficient()).nbrHour(item.getNbrHour())
					.typeExam(toExamTypeEnum(item.getTypeExam()));

			if (!StringUtils.isBlank(item.getSemesterId())) {
				assignClassEntity.semester(semesterRepository.getOne(item.getSemesterId()));
			}

			if (!StringUtils.isBlank(item.getPeriodId())) {
				assignClassEntity.period(periodRepository.getOne(item.getPeriodId()));
			}

			if (!StringUtils.isBlank(item.getClassId())) {
				Optional<ClassEntity> ClassEntity = classRepository.findById(item.getClassId());
				if (ClassEntity.isPresent()) {
					assignClassEntity.classs(ClassEntity.get());
				}
			}

			if (CollectionUtils.isNotEmpty(item.getTeacherIds())) {
				item.getTeacherIds().forEach(teacher -> {
					if (!StringUtils.isBlank(teacher)) {
						if (!teacher.equals("0")) {
							Optional<TeacherEntity> teacherEntity = teacherRepository.findById(teacher);
							if (teacherEntity.isPresent()) {
								teacherEntities.add(teacherEntity.get());
							}
						}
					}
				});
				assignClassEntity.teachers(teacherEntities);
			}
			assignClassEntities.add(assignClassEntity);
		});
		target.setAssignClasses(assignClassEntities);

	}

	public static ExamTypeEnum toExamTypeEnum(final String examType) {
		return ExamTypeEnum.forValue(examType);
	}

}
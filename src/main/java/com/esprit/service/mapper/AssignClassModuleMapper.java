package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassModuleEntity;
import com.esprit.dto.module.AssignClassModuleDTO;
import com.esprit.enums.ExamTypeEnum;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { SemesterMapper.class, PeriodMapper.class })
public interface AssignClassModuleMapper {

	AssignClassModuleMapper INSTANCE = Mappers.getMapper(AssignClassModuleMapper.class);

	AssignClassModuleDTO assignClassModuleEntityAssignClassModuleDTO(AssignClassModuleEntity assignClassModuleEntity);

	List<AssignClassModuleDTO> assignClassModuleAssignClassModuleDTO(
			List<AssignClassModuleEntity> assignClassModuleEntities);

	public default String toExamTypeString(final ExamTypeEnum examType) {
		String result = null;
		if (examType != null) {
			result = ExamTypeEnum.forKey(examType);
		}
		return result;
	}

}
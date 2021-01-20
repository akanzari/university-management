package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.AssignClassExamEntity;
import com.esprit.domain.ExamEntity;
import com.esprit.dto.exam.AssignClassExamDTO;
import com.esprit.dto.exam.ExamDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExamResponseMapper {

	ExamResponseMapper INSTANCE = Mappers.getMapper(ExamResponseMapper.class);

	ExamDTO examEntityToExamDTO(ExamEntity examEntity);

	List<ExamDTO> examEntitiesToExamDTO(List<ExamEntity> examEntity);

	AssignClassExamDTO assignClassExamEntityToAssignClassExamDTO(AssignClassExamEntity assignClassExamEntity);

	List<AssignClassExamDTO> assignClassExamEntitiesToAssignClassExamDTO(
			List<AssignClassExamEntity> assignClassExamEntities);
}
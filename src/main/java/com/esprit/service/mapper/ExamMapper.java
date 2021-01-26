package com.esprit.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.ExamEntity;
import com.esprit.dto.exam.CreateExamRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExamMapper {

	ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

	@Mapping(target = "classes", ignore = true)
	ExamEntity createExamRequestToExamEntity(CreateExamRequest createExamRequest);

}
package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.SemesterEntity;
import com.esprit.dto.SemesterDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PeriodMapper.class)
public interface SemesterMapper {

	SemesterMapper INSTANCE = Mappers.getMapper(SemesterMapper.class);

	SemesterEntity semesterDTOToSemesterEntity(SemesterDTO semesterDTO);

	SemesterDTO semesterEntityToSemesterDTO(SemesterEntity semesterEntity);

	List<SemesterDTO> semesterEntitiesToSemesterDTO(List<SemesterEntity> semesterEntities);

}
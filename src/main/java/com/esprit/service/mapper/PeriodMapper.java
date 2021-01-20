package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.PeriodEntity;
import com.esprit.dto.PeriodDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = WeekMapper.class)
public interface PeriodMapper {

	PeriodMapper INSTANCE = Mappers.getMapper(PeriodMapper.class);

	PeriodEntity periodDTOToPeriodEntity(PeriodDTO periodDTO);

	PeriodDTO periodEntityToPeriodDTO(PeriodEntity periodEntity);

	List<PeriodDTO> periodEntitiesToPeriodDTO(List<PeriodEntity> periodEntities);

}
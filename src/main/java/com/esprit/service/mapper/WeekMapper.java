package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.WeekEntity;
import com.esprit.dto.WeekDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeekMapper {

	WeekMapper INSTANCE = Mappers.getMapper(WeekMapper.class);

	WeekEntity weekDTOToweekEntity(WeekDTO weekDTO);

	WeekDTO weekEntityToweekDTO(WeekEntity weekEntity);

	List<WeekDTO> weekEntitiesToweekDTO(List<WeekEntity> weekEntities);
}
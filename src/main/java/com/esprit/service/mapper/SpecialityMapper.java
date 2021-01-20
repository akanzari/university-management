package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.SpecialityDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialityMapper {

	SpecialityMapper INSTANCE = Mappers.getMapper(SpecialityMapper.class);

	SpecialityDTO specialityEntityToSpecialityDTO(SpecialityEntity specialityEntity);

	List<SpecialityDTO> specialityEntitiesToSpecialityDTO(List<SpecialityEntity> specialityEntities);

	SpecialityEntity specialityDTOToSpecialityEntity(SpecialityDTO specialityDTO);

}
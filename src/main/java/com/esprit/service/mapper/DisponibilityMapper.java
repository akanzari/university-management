package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.DisponibilityEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.DisponibilityDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisponibilityMapper {

	DisponibilityMapper INSTANCE = Mappers.getMapper(DisponibilityMapper.class);

	DisponibilityEntity createDisponibilityRequestToDisponibilityEntity(
			CreateDisponibilityRequest createDispoibilityRequest);

	List<DisponibilityEntity> createDisponibilityRequestToDisponibilityEntities(
			List<DisponibilityDTO> createDisponibilityRequest);

	DisponibilityDTO disponibilityEntityToDisponibilityEntities(DisponibilityEntity disponibilityEntity);

	List<DisponibilityDTO> disponibilityEntitiesToDisponibilityEntities(
			List<DisponibilityEntity> disponibilityEntities);

}
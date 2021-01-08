package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialityMapper {

	SpecialityMapper INSTANCE = Mappers.getMapper(SpecialityMapper.class);

	SpecialityEntity createSpecialityRequestToSpecialityEntity(CreateSpecialityRequest createClassRoomRequest);

	SpecialityResponse specialityEntityToSpecialityResponse(SpecialityEntity specialityEntity);

	List<SpecialityResponse> specialityEntitiesToSpecialityResponse(List<SpecialityEntity> specialityEntities);

}
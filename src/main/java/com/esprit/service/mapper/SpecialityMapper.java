package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.esprit.domain.SpecialityEntity;
import com.esprit.dto.request.CreateSpecialityRequest;
import com.esprit.dto.response.SpecialityResponse;

@Mapper
public interface SpecialityMapper {

	SpecialityEntity createSpecialityRequestToSpecialityEntity(CreateSpecialityRequest createClassRoomRequest);

	SpecialityResponse specialityEntityToSpecialityResponse(SpecialityEntity specialityEntity);

	List<SpecialityResponse> specialityEntitiesToSpecialityResponse(List<SpecialityEntity> specialityEntities);

}
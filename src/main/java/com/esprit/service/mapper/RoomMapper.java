package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.RoomDTO;
import com.esprit.dto.room.RoomWithoutDisponibilityDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DisponibilityMapper.class)
public interface RoomMapper {

	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

	RoomEntity createRoomRequestToClassRoomEntity(CreateRoomRequest createRoomRequest);

	RoomEntity updateRoomRequestToClassRoomEntity(CreateRoomRequest createRoomRequest);

	@Mapping(target = "pole", expression = "java(classRoomEntity.getPole().equals(\"G\") ? \"El Ghazala\" : \"Charguia\")")
	RoomDTO classRoomEntityToClassRoomResponse(RoomEntity classRoomEntity);

	List<RoomDTO> classRoomEntitiesToClassRoomResponse(List<RoomEntity> classRoomEntities);

	@Mapping(target = "pole", expression = "java(roomWithoutDisponibilityDTO.getPole().equals(\"G\") ? \"El Ghazala\" : \"Charguia\")")
	RoomDTO roomWithoutDisponibilityDTOToRoomDTO(RoomWithoutDisponibilityDTO roomWithoutDisponibilityDTO);

	List<RoomDTO> roomWithoutDisponibilityDTOsToRoomDTO(List<RoomWithoutDisponibilityDTO> roomWithoutDisponibilityDTOs);

}
package com.esprit.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.esprit.domain.DisponibilityEntity;
import com.esprit.domain.RoomEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.dto.room.RoomWithoutDisponibilityDTO;
import com.esprit.service.PeriodService;
import com.esprit.service.SeanceService;
import com.esprit.service.SemesterService;
import com.esprit.service.WeekService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { SemesterService.class, SeanceService.class,
		WeekService.class, PeriodService.class })
public interface RoomMapper {

	RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

	RoomEntity createRoomRequestToClassRoomEntity(CreateRoomRequest createRoomRequest);

	RoomEntity updateRoomRequestToClassRoomEntity(CreateRoomRequest createRoomRequest);

	@Mapping(target = "pole", expression = "java(classRoomEntity.getPole().equals(\"g\") ? \"Charguia\" : \"\")")
	RoomDTO classRoomEntityToClassRoomResponse(RoomEntity classRoomEntity);

	List<RoomDTO> classRoomEntitiesToClassRoomResponse(List<RoomEntity> classRoomEntities);

	@Mapping(target = "pole", expression = "java(roomWithoutDisponibilityDTO.getPole().equals(\"g\") ? \"Charguia\" : \"El Ghazala\")")
	RoomDTO roomWithoutDisponibilityDTOToRoomDTO(RoomWithoutDisponibilityDTO roomWithoutDisponibilityDTO);

	List<RoomDTO> roomWithoutDisponibilityDTOsToRoomDTO(List<RoomWithoutDisponibilityDTO> roomWithoutDisponibilityDTOs);

	@Mapping(source = "semesterId", target = "semester", qualifiedByName = "findSemester")
	@Mapping(source = "seanceId", target = "seance", qualifiedByName = "findSeance")
	@Mapping(source = "weekId", target = "week", qualifiedByName = "findWeek")
	@Mapping(source = "periodId", target = "period", qualifiedByName = "findPeriod")
	DisponibilityEntity createDispoibilityRequestToDisponibilityEntity(
			CreateDisponibilityRequest createDispoibilityRequest);

	List<DisponibilityEntity> createDisponibilityRequestToDisponibilityEntities(
			List<DisponibilityDTO> createDisponibilityRequest);

	DisponibilityDTO disponibilityEntityToDisponibilityEntities(DisponibilityEntity disponibilityEntity);

	List<DisponibilityDTO> disponibilityEntitiesToDisponibilityEntities(
			List<DisponibilityEntity> disponibilityEntities);

}
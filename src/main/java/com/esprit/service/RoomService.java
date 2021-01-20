package com.esprit.service;

import java.util.Date;
import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.DisponibilityDTO;
import com.esprit.dto.room.RoomDTO;

public interface RoomService {

	void addClassRoom(CreateRoomRequest createRoomRequest);

	void updateClassRoom(CreateRoomRequest createRoomRequest);

	void deleteClassRoom(String roomId);

	RoomDTO findClassRoom(String roomId);

	List<RoomDTO> findByBloc(String bloc);

	@Named("findRoomsByIds")
	List<RoomEntity> findRoomsByIds(List<String> rooms);

	List<RoomDTO> findAllByBlocs(Date effectDate, int hour, List<String> blocs);

	List<RoomDTO> findRooms();

	List<RoomDTO> findRoomsWithoutDisponibilities();

	void addDisponibility(String classRoomId, CreateDisponibilityRequest createDispoibilityRequest);

	void updateDisponibility(CreateDisponibilityRequest createDispoibilityRequest);

	void deleteDisponibility(String disponibilityId);

	List<DisponibilityDTO> findDisponibilitiesByRoomId(String roomId);

}
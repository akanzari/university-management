package com.esprit.service;

import java.util.List;

import org.mapstruct.Named;

import com.esprit.domain.RoomEntity;
import com.esprit.dto.room.CreateRoomRequest;
import com.esprit.dto.room.FilterAvailableRoomDTO;
import com.esprit.dto.room.RoomDTO;

public interface RoomService {

	void addClassRoom(CreateRoomRequest createRoomRequest);

	void updateClassRoom(CreateRoomRequest createRoomRequest);

	void deleteClassRoom(String roomId);

	RoomDTO findClassRoom(String roomId);

	List<RoomDTO> findByBloc(String bloc);

	@Named("findRoomsByIds")
	List<RoomEntity> findRoomsByIds(List<String> rooms);

	List<RoomDTO> findAllByBlocs(FilterAvailableRoomDTO filterAvailableRoomDTO);

	List<RoomDTO> findRooms();
	
	List<String> findBlocByRooms(List<String> rooms);

}
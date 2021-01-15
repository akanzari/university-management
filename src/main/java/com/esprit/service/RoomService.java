package com.esprit.service;

import java.util.List;

import com.esprit.dto.RoomDTO;

public interface RoomService {

	void addClassRoom(RoomDTO roomDTO);

	void updateClassRoom(RoomDTO roomDTO);

	void deleteClassRoom(String classRoomId);

	RoomDTO findClassRoom(String classRoomId);

	List<RoomDTO> findClassRooms();

}
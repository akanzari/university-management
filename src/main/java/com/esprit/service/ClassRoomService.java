package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.rooms.CreateClassRoomRequest;
import com.esprit.dto.request.rooms.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;

public interface ClassRoomService {

	void addClassRoom(CreateClassRoomRequest createClassRoomRequest);

	void updateClassRoom(UpdateClassRoomRequest updateClassRoomRequest);

	void deleteClassRoom(String classRoomId);

	ClassRoomResponse findClassRoom(String classRoomId);

	List<ClassRoomResponse> findClassRooms();

}
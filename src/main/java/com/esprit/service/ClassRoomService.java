package com.esprit.service;

import java.util.List;

import com.esprit.dto.request.CreateClassRoomRequest;
import com.esprit.dto.request.UpdateClassRoomRequest;
import com.esprit.dto.response.ClassRoomResponse;

public interface ClassRoomService {

	ClassRoomResponse addClassRoom(CreateClassRoomRequest createClassRoomRequest);

	ClassRoomResponse updateClassRoom(UpdateClassRoomRequest updateClassRoomRequest);

	void deleteClassRoom(String classRoomId);

	ClassRoomResponse findClassRoom(String classRoomId);

	List<ClassRoomResponse> findClassRooms();

}
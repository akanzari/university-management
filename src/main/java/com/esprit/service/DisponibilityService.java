package com.esprit.service;

import java.util.List;

import com.esprit.dto.CreateDisponibilityRequest;
import com.esprit.dto.room.DisponibilityDTO;

public interface DisponibilityService {

	void addDisponibilityToRoom(String roomId, CreateDisponibilityRequest createDispoibilityRequest);

	void addDisponibilityToTeacher(String teacherId, CreateDisponibilityRequest createDispoibilityRequest);

	void updateDisponibility(CreateDisponibilityRequest createDispoibilityRequest);

	void deleteDisponibilityFromTeacher(String teacherId, String disponibilityId);

	void deleteDisponibilityFromRoom(String roomId, String disponibilityId);

	List<DisponibilityDTO> findAllByTeacher(String teacherId);

	List<DisponibilityDTO> findAllByRoom(String roomId);

}
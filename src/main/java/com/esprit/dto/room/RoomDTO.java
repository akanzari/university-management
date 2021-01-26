package com.esprit.dto.room;

import java.util.List;

public class RoomDTO {

	private String classRoomId;

	private String label;

	private int capacity;

	private String pole;

	private String bloc;

	private List<DisponibilityDTO> disponibilities;

	public String getClassRoomId() {
		return classRoomId;
	}

	public RoomDTO classRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public RoomDTO label(String label) {
		this.label = label;
		return this;
	}

	public int getCapacity() {
		return capacity;
	}

	public RoomDTO capacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public String getPole() {
		return pole;
	}

	public RoomDTO pole(String pole) {
		this.pole = pole;
		return this;
	}

	public String getBloc() {
		return bloc;
	}

	public RoomDTO bloc(String bloc) {
		this.bloc = bloc;
		return this;
	}

	public List<DisponibilityDTO> getDisponibilities() {
		return disponibilities;
	}

	public RoomDTO disponibilities(List<DisponibilityDTO> disponibilities) {
		this.disponibilities = disponibilities;
		return this;
	}

}
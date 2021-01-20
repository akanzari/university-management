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

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getPole() {
		return pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public String getBloc() {
		return bloc;
	}

	public void setBloc(String bloc) {
		this.bloc = bloc;
	}

	public List<DisponibilityDTO> getDisponibilities() {
		return disponibilities;
	}

	public void setDisponibilities(List<DisponibilityDTO> disponibilities) {
		this.disponibilities = disponibilities;
	}

}
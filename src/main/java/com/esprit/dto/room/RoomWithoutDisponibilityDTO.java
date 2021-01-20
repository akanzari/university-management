package com.esprit.dto.room;

public class RoomWithoutDisponibilityDTO {

	private String classRoomId;

	private String label;

	private int capacity;

	private String pole;

	private String bloc;

	public RoomWithoutDisponibilityDTO(String classRoomId, String label, int capacity, String pole, String bloc) {
		this.classRoomId = classRoomId;
		this.label = label;
		this.capacity = capacity;
		this.pole = pole;
		this.bloc = bloc;
	}

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

}
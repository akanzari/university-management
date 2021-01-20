package com.esprit.dto.seance;

public class CreateSeanceRequest {

	private String seanceId;

	private int startHour;

	private int endHour;

	public String getSeanceId() {
		return seanceId;
	}

	public void setSeanceId(String seanceId) {
		this.seanceId = seanceId;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

}
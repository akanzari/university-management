package com.esprit.dto.teacher;

import java.util.Date;

public class DisponibilityTeacherDTO {
	
	private String disponibilityId;

	private Date supervisionDate;

	private int supervisionHour;

	private int supervisionDuration;

	public String getDisponibilityId() {
		return disponibilityId;
	}

	public void setDisponibilityId(String disponibilityId) {
		this.disponibilityId = disponibilityId;
	}

	public Date getSupervisionDate() {
		return supervisionDate;
	}

	public void setSupervisionDate(Date supervisionDate) {
		this.supervisionDate = supervisionDate;
	}

	public int getSupervisionHour() {
		return supervisionHour;
	}

	public void setSupervisionHour(int supervisionHour) {
		this.supervisionHour = supervisionHour;
	}

	public int getSupervisionDuration() {
		return supervisionDuration;
	}

	public void setSupervisionDuration(int supervisionDuration) {
		this.supervisionDuration = supervisionDuration;
	}

}
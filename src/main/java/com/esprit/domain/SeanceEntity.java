package com.esprit.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEANCE")
public class SeanceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
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
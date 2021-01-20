package com.esprit.dto;

import java.util.Date;

public class CreateDisponibilityRequest {

	private String disponibilityId;

	private String day;

	private String seanceId;

	private String weekId;

	private String semesterId;

	private String year;

	private String periodId;

	private String motif;

	private Date exactDate;

	public String getDisponibilityId() {
		return disponibilityId;
	}

	public void setDisponibilityId(String disponibilityId) {
		this.disponibilityId = disponibilityId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSeanceId() {
		return seanceId;
	}

	public void setSeanceId(String seanceId) {
		this.seanceId = seanceId;
	}

	public String getWeekId() {
		return weekId;
	}

	public void setWeekId(String weekId) {
		this.weekId = weekId;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Date getExactDate() {
		return exactDate;
	}

	public void setExactDate(Date exactDate) {
		this.exactDate = exactDate;
	}

}
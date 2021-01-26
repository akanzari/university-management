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

	private int startHour;

	private int endHour;

	private Date startDate;

	private Date endDate;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
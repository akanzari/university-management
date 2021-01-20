package com.esprit.dto.room;

import com.esprit.dto.PeriodDTO;
import com.esprit.dto.SemesterDTO;
import com.esprit.dto.WeekDTO;
import com.esprit.dto.seance.SeanceDTO;

public class DisponibilityDTO {

	private String disponibilityId;

	private String day;

	private SeanceDTO seance;

	private WeekDTO week;

	private SemesterDTO semester;

	private PeriodDTO period;

	private String year;

	private String motif;

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

	public SeanceDTO getSeance() {
		return seance;
	}

	public void setSeance(SeanceDTO seance) {
		this.seance = seance;
	}

	public WeekDTO getWeek() {
		return week;
	}

	public void setWeek(WeekDTO week) {
		this.week = week;
	}

	public SemesterDTO getSemester() {
		return semester;
	}

	public void setSemester(SemesterDTO semester) {
		this.semester = semester;
	}

	public PeriodDTO getPeriod() {
		return period;
	}

	public void setPeriod(PeriodDTO period) {
		this.period = period;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

}
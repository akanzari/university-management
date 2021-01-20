package com.esprit.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DISPONIBILITY_SALLE")
public class DisponibilityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "disponibility-uuid")
	@GenericGenerator(name = "disponibility-uuid", strategy = "uuid2")
	private String disponibilityId;

	private String day;

	private String year;

	private String motif;

	private Date exactDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_seance")
	private SeanceEntity seance;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_week")
	private WeekEntity week;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_semester")
	private SemesterEntity semester;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_period")
	private PeriodEntity period;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_room")
	private RoomEntity room;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_teacher")
	private TeacherEntity teacher;

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

	public Date getExactDate() {
		return exactDate;
	}

	public void setExactDate(Date exactDate) {
		this.exactDate = exactDate;
	}

	public SeanceEntity getSeance() {
		return seance;
	}

	public void setSeance(SeanceEntity seance) {
		this.seance = seance;
	}

	public WeekEntity getWeek() {
		return week;
	}

	public void setWeek(WeekEntity week) {
		this.week = week;
	}

	public SemesterEntity getSemester() {
		return semester;
	}

	public void setSemester(SemesterEntity semester) {
		this.semester = semester;
	}

	public PeriodEntity getPeriod() {
		return period;
	}

	public void setPeriod(PeriodEntity period) {
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

	public RoomEntity getRoom() {
		return room;
	}

	public void setRoom(RoomEntity room) {
		this.room = room;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

}
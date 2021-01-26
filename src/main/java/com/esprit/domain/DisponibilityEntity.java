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
@Table(name = "DISPONIBILITY")
public class DisponibilityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "disponibility-uuid")
	@GenericGenerator(name = "disponibility-uuid", strategy = "uuid2")
	private String disponibilityId;

	private String motif;

	private Date startDate;

	private Date endDate;

	private int startHour;

	private int endHour;

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

	public DisponibilityEntity disponibilityId(String disponibilityId) {
		this.disponibilityId = disponibilityId;
		return this;
	}

	public String getMotif() {
		return motif;
	}

	public DisponibilityEntity motif(String motif) {
		this.motif = motif;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public DisponibilityEntity startDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public DisponibilityEntity endDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public int getStartHour() {
		return startHour;
	}

	public DisponibilityEntity startHour(int startHour) {
		this.startHour = startHour;
		return this;
	}

	public int getEndHour() {
		return endHour;
	}

	public DisponibilityEntity endHour(int endHour) {
		this.endHour = endHour;
		return this;
	}

	public RoomEntity getRoom() {
		return room;
	}

	public DisponibilityEntity room(RoomEntity room) {
		this.room = room;
		return this;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public DisponibilityEntity teacher(TeacherEntity teacher) {
		this.teacher = teacher;
		return this;
	}

}
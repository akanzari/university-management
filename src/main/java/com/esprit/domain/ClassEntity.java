package com.esprit.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLASSE")
public class ClassEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String classId;

	private String label;

	private int nbrStudents;

	private Date examDate;

	private int examHour;

	private int examDuration;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "fk_module")
	private ModuleEntity module;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_surveillance_teacher")
	private List<TeacherEntity> teachers;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_surveillance_room")
	private List<RoomEntity> rooms;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fk_speciality")
	private SpecialityEntity speciality;

	@JsonIgnore
	@ManyToMany(mappedBy = "classes")
	private List<ExamEntity> examEntities;

	private String category;

	private String email;

	public String getClassId() {
		return classId;
	}

	public ClassEntity classId(String classId) {
		this.classId = classId;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public ClassEntity label(String label) {
		this.label = label;
		return this;
	}

	public int getNbrStudents() {
		return nbrStudents;
	}

	public ClassEntity nbrStudents(int nbrStudents) {
		this.nbrStudents = nbrStudents;
		return this;
	}

	public SpecialityEntity getSpeciality() {
		return speciality;
	}

	public ClassEntity speciality(SpecialityEntity speciality) {
		this.speciality = speciality;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public ClassEntity category(String category) {
		this.category = category;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public ClassEntity email(String email) {
		this.email = email;
		return this;
	}

	public Date getExamDate() {
		return examDate;
	}

	public ClassEntity examDate(Date examDate) {
		this.examDate = examDate;
		return this;
	}

	public int getExamHour() {
		return examHour;
	}

	public ClassEntity examHour(int examHour) {
		this.examHour = examHour;
		return this;
	}

	public int getExamDuration() {
		return examDuration;
	}

	public ClassEntity examDuration(int examDuration) {
		this.examDuration = examDuration;
		return this;
	}

	public ModuleEntity getModule() {
		return module;
	}

	public ClassEntity module(ModuleEntity module) {
		this.module = module;
		return this;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public ClassEntity teachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
		return this;
	}

	public List<RoomEntity> getRooms() {
		return rooms;
	}

	public ClassEntity rooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
		return this;
	}

	public List<ExamEntity> getExamEntities() {
		return examEntities;
	}

	public ClassEntity examEntities(List<ExamEntity> examEntities) {
		this.examEntities = examEntities;
		return this;
	}

}
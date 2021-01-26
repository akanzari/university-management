package com.esprit.dto.classes;

import java.util.Date;
import java.util.List;

import com.esprit.dto.SpecialityDTO;
import com.esprit.dto.module.ModuleDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.dto.teacher.TeacherDTO;

public class ClassDTO {

	private String classId;

	private String label;

	private SpecialityDTO speciality;

	private String category;

	private String email;

	private String typeExam;

	private int nbrStudents;

	private Date examDate;

	private int examHour;

	private int examDuration;

	private ModuleDTO module;

	private List<TeacherDTO> teachers;

	private List<RoomDTO> rooms;

	public String getClassId() {
		return classId;
	}

	public ClassDTO classId(String classId) {
		this.classId = classId;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public ClassDTO label(String label) {
		this.label = label;
		return this;
	}

	public SpecialityDTO getSpeciality() {
		return speciality;
	}

	public ClassDTO speciality(SpecialityDTO speciality) {
		this.speciality = speciality;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public ClassDTO category(String category) {
		this.category = category;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public ClassDTO email(String email) {
		this.email = email;
		return this;
	}

	public int getNbrStudents() {
		return nbrStudents;
	}

	public ClassDTO nbrStudents(int nbrStudents) {
		this.nbrStudents = nbrStudents;
		return this;
	}

	public Date getExamDate() {
		return examDate;
	}

	public ClassDTO examDate(Date examDate) {
		this.examDate = examDate;
		return this;
	}

	public int getExamHour() {
		return examHour;
	}

	public ClassDTO examHour(int examHour) {
		this.examHour = examHour;
		return this;
	}

	public int getExamDuration() {
		return examDuration;
	}

	public ClassDTO examDuration(int examDuration) {
		this.examDuration = examDuration;
		return this;
	}

	public ModuleDTO getModule() {
		return module;
	}

	public ClassDTO module(ModuleDTO module) {
		this.module = module;
		return this;
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}

	public ClassDTO teachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
		return this;
	}

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public ClassDTO rooms(List<RoomDTO> rooms) {
		this.rooms = rooms;
		return this;
	}

	public String getTypeExam() {
		return typeExam;
	}

	public ClassDTO typeExam(String typeExam) {
		this.typeExam = typeExam;
		return this;
	}

}
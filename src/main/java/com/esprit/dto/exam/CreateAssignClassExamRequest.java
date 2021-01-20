package com.esprit.dto.exam;

import java.util.Date;
import java.util.List;

public class CreateAssignClassExamRequest {

	private Date examDate;

	private int examHour;

	private int examDuration;

	private String module;

	private List<String> classes;

	private List<String> teachers;

	private List<String> rooms;

	private List<String> bloc;

	private List<String> up;

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public int getExamHour() {
		return examHour;
	}

	public void setExamHour(int examHour) {
		this.examHour = examHour;
	}

	public int getExamDuration() {
		return examDuration;
	}

	public void setExamDuration(int examDuration) {
		this.examDuration = examDuration;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public List<String> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<String> teachers) {
		this.teachers = teachers;
	}

	public List<String> getRooms() {
		return rooms;
	}

	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}

	public List<String> getBloc() {
		return bloc;
	}

	public void setBloc(List<String> bloc) {
		this.bloc = bloc;
	}

	public List<String> getUp() {
		return up;
	}

	public void setUp(List<String> up) {
		this.up = up;
	}

}
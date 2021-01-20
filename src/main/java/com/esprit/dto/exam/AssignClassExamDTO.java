package com.esprit.dto.exam;

import java.util.Date;
import java.util.List;

import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.module.ModuleDTO;
import com.esprit.dto.room.RoomDTO;
import com.esprit.dto.teacher.TeacherDTO;

public class AssignClassExamDTO {

	private String assignId;

	private Date examDate;

	private int examHour;

	private int examDuration;

	private ModuleDTO module;

	private List<TeacherDTO> teachers;

	private List<RoomDTO> rooms;

	private List<ClassDTO> classes;

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

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

	public ModuleDTO getModule() {
		return module;
	}

	public void setModule(ModuleDTO module) {
		this.module = module;
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
	}

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomDTO> rooms) {
		this.rooms = rooms;
	}

	public List<ClassDTO> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassDTO> classes) {
		this.classes = classes;
	}

}
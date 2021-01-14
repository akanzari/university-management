package com.esprit.dto.request.exams;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateExamRequest {

	@NotBlank
	private String classId;

	@NotBlank
	private String moduleId;

	@NotNull
	private Date examDate;

	@NotNull
	private int examHour;

	@NotNull
	private int examDuration;

	@NotBlank
	private String dsex;

	@NotBlank
	private String examType;

	@NotBlank
	private String semester;

	@NotBlank
	private String examSession;

	@NotBlank
	private String classRoomId;

	@NotBlank
	private String supervisorId;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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

	public String getDsex() {
		return dsex;
	}

	public void setDsex(String dsex) {
		this.dsex = dsex;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getExamSession() {
		return examSession;
	}

	public void setExamSession(String examSession) {
		this.examSession = examSession;
	}

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

}
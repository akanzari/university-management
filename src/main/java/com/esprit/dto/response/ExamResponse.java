package com.esprit.dto.response;

import java.util.Date;

import com.esprit.dto.ClassDTO;
import com.esprit.dto.TeacherDTO;

public class ExamResponse {

	private String examId;

	private Date examDate;

	private int examHour;

	private int examDuration;

	private String examType;

	private String semester;

	private String examSession;

	private String dsex;

	private ClassDTO classs;

	private ModuleResponse module;

	private ClassRoomResponse classRoom;

	private TeacherDTO supervisor;

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
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

	public String getDsex() {
		return dsex;
	}

	public void setDsex(String dsex) {
		this.dsex = dsex;
	}

	public ClassDTO getClasss() {
		return classs;
	}

	public void setClasss(ClassDTO classs) {
		this.classs = classs;
	}

	public ModuleResponse getModule() {
		return module;
	}

	public void setModule(ModuleResponse module) {
		this.module = module;
	}

	public ClassRoomResponse getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoomResponse classRoom) {
		this.classRoom = classRoom;
	}

	public TeacherDTO getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(TeacherDTO supervisor) {
		this.supervisor = supervisor;
	}

}
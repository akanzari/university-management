package com.esprit.dto.module;

import java.util.List;

import com.esprit.dto.PeriodDTO;
import com.esprit.dto.SemesterDTO;
import com.esprit.dto.classes.ClassDTO;
import com.esprit.dto.teacher.TeacherDTO;

public class AssignClassModuleDTO {

	private String assignId;

	private int coefficient;

	private int nbrHour;

	private SemesterDTO semester;

	private PeriodDTO period;

	private String typeExam;

	private List<TeacherDTO> teachers;

	private ClassDTO classs;

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getNbrHour() {
		return nbrHour;
	}

	public void setNbrHour(int nbrHour) {
		this.nbrHour = nbrHour;
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

	public String getTypeExam() {
		return typeExam;
	}

	public void setTypeExam(String typeExam) {
		this.typeExam = typeExam;
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
	}

	public ClassDTO getClasss() {
		return classs;
	}

	public void setClasss(ClassDTO classs) {
		this.classs = classs;
	}

}
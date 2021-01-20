package com.esprit.dto.module;

import java.util.List;

public class AssignClassToModuleRequest {

	private int coefficient;

	private int nbrHour;

	private String semesterId;

	private String periodId;

	private String typeExam;

	private List<String> teacherIds;

	private String classId;

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

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getTypeExam() {
		return typeExam;
	}

	public void setTypeExam(String typeExam) {
		this.typeExam = typeExam;
	}

	public List<String> getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(List<String> teacherIds) {
		this.teacherIds = teacherIds;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

}
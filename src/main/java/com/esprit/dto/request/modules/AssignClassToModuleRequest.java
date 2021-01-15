package com.esprit.dto.request.modules;

import java.util.List;

public class AssignClassToModuleRequest {

	private int coefficient;

	private int nbrHour;

	private String semester;

	private String period;

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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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